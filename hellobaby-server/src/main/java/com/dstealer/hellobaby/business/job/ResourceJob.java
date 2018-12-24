package com.dstealer.hellobaby.business.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dstealer.hellobaby.business.job.vo.FormattedCpuPerc;
import com.dstealer.hellobaby.business.job.vo.FormattedFileSystemUsage;
import com.dstealer.hellobaby.business.job.vo.FormattedMem;
import com.dstealer.hellobaby.common.CommUtil;
import com.dstealer.hellobaby.common.Constant;
import com.dstealer.hellobaby.common.MultiRingBuffer;
import com.dstealer.hellobaby.database.pojo.DirMonitorPojo;
import com.dstealer.hellobaby.database.pojo.MemMonitorPojo;
import com.dstealer.hellobaby.notify.WechatNotify;
import com.dstealer.hellobaby.template.MessageEngine;
import com.dstealer.hellobaby.client.biz.RemoteSigarService;
import com.dstealer.hellobaby.database.dao.SystemBaseDao;
import com.dstealer.hellobaby.database.pojo.CPUMonitorPojo;
import com.dstealer.hellobaby.database.pojo.HostCfgPojo;
import com.dstealer.hellobaby.service.SigarServiceManager;
import org.hyperic.sigar.*;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 系统资源监控
 * Created by DStealer on 4/17/17.
 */
@DisallowConcurrentExecution
public class ResourceJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceJob.class);
    private static final AtomicLong COUNTER = new AtomicLong();
    private static final MultiRingBuffer<String, FormattedCpuPerc> MULTI_CPU_PERCS = new MultiRingBuffer<>(12);
    private static final MultiRingBuffer<String, FormattedMem> MULTI_MEMS = new MultiRingBuffer<>(12);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("Resource job:{} start", jobExecutionContext.getJobDetail().getKey().getName());
        COUNTER.incrementAndGet();//计数器自增
        try {
            List<HostCfgPojo> hostCfgPojoList = SystemBaseDao.getHostCfg();
            for (HostCfgPojo pojo : hostCfgPojoList) {
                if (pojo.isEnable()) {
                    try {
                        LOGGER.info("Check {}@{} resource start ...", pojo.getHostName(), pojo.getIp());
                        MessageEngine messageEngine = (MessageEngine) jobExecutionContext.getScheduler().getContext().get(Constant.KEY_MESSAGE_ENGINE);
                        diskUsageMonitor(pojo, messageEngine);
                        cpuUsageMonitor(pojo, messageEngine);
                        memUsageMonitor(pojo, messageEngine);
                        LOGGER.info("Check {}@{} resource end ...", pojo.getHostName(), pojo.getIp());
                    } catch (Exception e) {
                        LOGGER.warn("Error in check {}@{}", pojo.getHostName(), pojo.getIp());
                        if (COUNTER.get() % 2L == 0L) {
                            WechatNotify.notify("Resource job for:%s@%s encounter error", pojo.getHostName(), pojo.getIp());
                        }
                    }
                } else {
                    LOGGER.warn("Host monitor is disable,{}", pojo.toString());
                }
            }
            LOGGER.info("Resource job:{} end", jobExecutionContext.getJobDetail().getKey().getName());
        } catch (Exception e) {
            LOGGER.error("Error in execute job", e);
            WechatNotify.notify("Resource job encounter error");
        }
    }

    /**
     * 磁盘使用监控
     * 限制:路径必须是一个磁盘的挂载点
     * TODO 区别不同主机监控路径
     *
     * @param hostCfgPojo
     * @param messageEngine
     * @throws Exception
     */
    private void diskUsageMonitor(HostCfgPojo hostCfgPojo, MessageEngine messageEngine) throws Exception {
        LOGGER.info("Check disk usage for {}@{} start...", hostCfgPojo.getHostName(), hostCfgPojo.getIp());
        RemoteSigarService service = SigarServiceManager.get(hostCfgPojo.getIp(), hostCfgPojo.getPort());
        //磁盘空间监控
        List<DirMonitorPojo> dirMonitorPojoList = SystemBaseDao.getDirMonitor(hostCfgPojo.getHostName());
        FileSystemMap mounts = service.getFileSystemMap();
        for (DirMonitorPojo pojo : dirMonitorPojoList) {
            if (pojo.isEnable()) {
                FileSystem fs = mounts.getMountPoint(pojo.getDirName());
                //限制监控的本地磁盘的挂载点,
                if (fs != null && fs.getDirName().equals(pojo.getDirName()) && fs.getType() == FileSystem.TYPE_LOCAL_DISK) {
                    FileSystemUsage usage = service.getFileSystemUsage(fs.getDirName());
                    JSONObject data = new JSONObject();
                    data.put("host", JSON.toJSON(hostCfgPojo));
                    data.put("sample", JSON.toJSON(new FormattedFileSystemUsage(pojo, usage)));
                    LOGGER.info("Disk usage:{}", data.toJSONString());
                    //使用计数器控制报警的频率
                    if (pojo.getThreshold() <= usage.getUsePercent()) {
                        LOGGER.warn("Disk usage is warning:{}", data.toJSONString());
                        if (COUNTER.get() % 2L == 0L) {
                            WechatNotify.notify(messageEngine.process(pojo.getTemplate(), data));
                        }
                    } else {
                        LOGGER.info("Disk usage is normal,current usage rate:{},threshold:{}",
                                CommUtil.formatPercent(usage.getUsePercent()), CommUtil.formatPercent(pojo.getThreshold()));
                    }
                } else {
                    throw new InvalidParameterException(pojo.getDirName() + " No such file or directory or not mount point!");
                }
            } else {
                LOGGER.warn("Item:{} is disable", pojo.getId());
            }
        }
        LOGGER.info("Check disk usage for {}@{} end...", hostCfgPojo.getHostName(), hostCfgPojo.getIp());
    }

    /**
     * CPU使用情况监控
     * 这里应该排除瞬时值对采样的影响,采用排除最大值和最小值后取平均值的方法
     * TODO 区别不同主机监控路径
     *
     * @param hostCfgPojo
     * @param messageEngine
     * @throws Exception
     */
    private void cpuUsageMonitor(HostCfgPojo hostCfgPojo, MessageEngine messageEngine) throws Exception {
        LOGGER.info("Check cpu usage for {}@{} start...", hostCfgPojo.getHostName(), hostCfgPojo.getIp());
        RemoteSigarService service = SigarServiceManager.get(hostCfgPojo.getIp(), hostCfgPojo.getPort());
        CpuPerc cpuPerc = service.getCpuPerc();
        CPUMonitorPojo pojo = SystemBaseDao.findCPUMonitor(hostCfgPojo.getHostName());
        String bufferKey = hostCfgPojo.getHostName();
        if (pojo == null || !pojo.isEnable()) {
            //清空监控集合
            MULTI_CPU_PERCS.removeBuffer(bufferKey);
            LOGGER.warn("CPU monitor:{} is disable");
        } else {
            //每次记录监控信息
            FormattedCpuPerc formattedCpuPerc = new FormattedCpuPerc(new Date(), cpuPerc);
            MULTI_CPU_PERCS.putIfBufferAbsent(bufferKey, formattedCpuPerc);
            LOGGER.info("CPU usage:{}", JSON.toJSONString(formattedCpuPerc));
            //检测是否需要报警
            FormattedCpuPerc[] cpuPercs = MULTI_CPU_PERCS.getAsArray(bufferKey, new FormattedCpuPerc[0]);
            double[] usageRates = new double[cpuPercs.length];
            for (int i = 0; i < usageRates.length; i++) {
                usageRates[i] = cpuPercs[i].getOriginalCombined();
            }
            double avgRate = CommUtil.avgExMaxMin(usageRates);
            if (pojo.getThreshold() <= avgRate) { //平均使用率超限
                JSONObject data = new JSONObject();
                data.put("host", JSON.toJSON(hostCfgPojo));
                data.put("threshold", CommUtil.formatPercent(pojo.getThreshold()));
                data.put("average", CommUtil.formatPercent(avgRate));
                data.put("sample", new JSONArray(Arrays.asList(cpuPercs)));
                LOGGER.warn("CPU usage is warning,{}", data.toJSONString());
                //使用计数器控制报警的频率
                if (COUNTER.get() % 2L == 0L) {
                    WechatNotify.notify(messageEngine.process(pojo.getTemplate(), data));
                }
            } else {
                LOGGER.info("CPU usage is normal, average rate:{},threshold:{}", CommUtil.formatPercent(avgRate), CommUtil.formatPercent(pojo.getThreshold()));
            }
        }
        LOGGER.info("Check cpu usage for {}@{} end...", hostCfgPojo.getHostName(), hostCfgPojo.getIp());
    }

    /**
     * Mem使用空间监控
     *
     * @param hostCfgPojo
     * @param messageEngine
     * @throws Exception
     */
    private void memUsageMonitor(HostCfgPojo hostCfgPojo, MessageEngine messageEngine) throws Exception {
        LOGGER.info("Check mem usage for {}@{} start...", hostCfgPojo.getHostName(), hostCfgPojo.getIp());
        RemoteSigarService service = SigarServiceManager.get(hostCfgPojo.getIp(), hostCfgPojo.getPort());
        MemMonitorPojo pojo = SystemBaseDao.findMemMonitor(hostCfgPojo.getHostName());
        String bufferKey = hostCfgPojo.getHostName();
        if (pojo == null || !pojo.isEnable()) {
            //清空监控集合
            MULTI_MEMS.removeBuffer(bufferKey);
            LOGGER.warn("Mem monitor:{} is disable");
        } else {
            //每次采集记录
            Mem mem = service.getMem();
            Swap swap = service.getSwap();
            FormattedMem fm = new FormattedMem(new Date(), mem, swap);
            LOGGER.info("Mem usage:{}", JSON.toJSONString(fm));
            MULTI_MEMS.putIfBufferAbsent(bufferKey, fm);
            //检测是否需要报警
            FormattedMem[] mems = MULTI_MEMS.getAsArray(bufferKey, new FormattedMem[0]);
            double[] usageRates = new double[mems.length];
            for (int i = 0; i < mems.length; i++) {
                usageRates[i] = mems[i].getOriginalActualUsedPer();
            }
            double avgRate = CommUtil.avgExMaxMin(usageRates);
            if (pojo.getThreshold() <= avgRate) {
                //使用计数器控制报警的频率
                JSONObject data = new JSONObject();
                data.put("host", JSON.toJSON(hostCfgPojo));
                data.put("threshold", CommUtil.formatPercent(pojo.getThreshold()));
                data.put("average", CommUtil.formatPercent(avgRate));
                data.put("sample", new JSONArray(Arrays.asList(mems)));
                LOGGER.warn("Mem usage is warning,{}", data.toJSONString());
                if (COUNTER.get() % 2L == 0L) {
                    WechatNotify.notify(messageEngine.process(pojo.getTemplate(), data));
                }
            } else {
                LOGGER.info("Mem usage is normal, average rate:{},threshold:{}", CommUtil.formatPercent(avgRate), CommUtil.formatPercent(pojo.getThreshold()));
            }

        }
        LOGGER.info("Check mem usage for {}@{} end...", hostCfgPojo.getHostName(), hostCfgPojo.getIp());
    }
}
