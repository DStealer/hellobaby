package com.dstealer.hellobaby.business.job;

import com.dstealer.hellobaby.common.Constant;
import com.dstealer.hellobaby.notify.SMSNotify;
import com.dstealer.hellobaby.notify.WechatNotify;
import com.dstealer.hellobaby.template.MessageEngine;
import com.dstealer.hellobaby.database.dao.CommonBaseDao;
import com.dstealer.hellobaby.database.dao.SystemBaseDao;
import com.dstealer.hellobaby.database.pojo.EventMonitorPojo;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 监控交易事件
 * Created by LiShiwu on 02/18/2017.
 */
@DisallowConcurrentExecution
public class MonitorJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("Monitor job:{} start", jobExecutionContext.getJobDetail().getKey().getName());
        try {
            MessageEngine messageEngine = (MessageEngine) jobExecutionContext.getScheduler().getContext().get(Constant.KEY_MESSAGE_ENGINE);
            List<EventMonitorPojo> pojoList = SystemBaseDao.getEventMonitorByJobName(jobExecutionContext.getJobDetail().getKey().getName());
            for (EventMonitorPojo pojo : pojoList) {
                if (pojo.isEnable()) {
                    LOGGER.info("Check task:{} {}", pojo.getId(), pojo.getDesc());
                    List<Map<String, String>> result = CommonBaseDao.executeQuery(pojo.getSql(), pojo.getDatasource());
                    if (!result.isEmpty()) {
                        String alertMessage = messageEngine.process(pojo.getTemplate(), result);
                        LOGGER.warn("Alarm message:{}", alertMessage);
                        switch (pojo.getNotifyType()) {
                            case WECHAT:
                                WechatNotify.notify(alertMessage);
                                break;
                            case SMS:
                                SMSNotify.notify(alertMessage);
                            default:
                                throw new UnsupportedOperationException("Unsupported notify type");
                        }
                    }
                }
            }
            LOGGER.info("Monitor job:{} end", jobExecutionContext.getJobDetail().getKey().getName());
        } catch (Exception e) {
            LOGGER.error("Error in execute job", e);
            WechatNotify.notify("Monitor job encounter error");
        }
    }
}
