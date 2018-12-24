package com.dstealer.hellobaby.business.job;

import com.dstealer.hellobaby.common.Constant;
import com.dstealer.hellobaby.common.ExportUtil;
import com.dstealer.hellobaby.notify.MailNotify;
import com.dstealer.hellobaby.notify.WechatNotify;
import com.dstealer.hellobaby.template.MessageEngine;
import com.dstealer.hellobaby.database.dao.CommonBaseDao;
import com.dstealer.hellobaby.database.dao.SystemBaseDao;
import com.dstealer.hellobaby.database.pojo.AttachmentType;
import com.dstealer.hellobaby.database.pojo.EventMailAttachmentPojo;
import com.dstealer.hellobaby.database.pojo.EventMailPojo;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 监控邮件事件
 * Created by LiShiwu on 02/18/2017.
 */
@DisallowConcurrentExecution
public class MailJob implements Job {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LOGGER.info("Mail job:{} start", jobExecutionContext.getJobDetail().getKey().getName());
        try {
            MessageEngine messageEngine = (MessageEngine) jobExecutionContext.getScheduler().getContext().get(Constant.KEY_MESSAGE_ENGINE);
            List<EventMailPojo> pojoList = SystemBaseDao.getEventMailByJobName(jobExecutionContext.getJobDetail().getKey().getName());
            for (EventMailPojo pojo : pojoList) {
                if (pojo.isEnable()) {
                    LOGGER.info("Execute task:{} {}", pojo.getId(), pojo.getDesc());
                    List<Map<String, String>> result = CommonBaseDao.executeQuery(pojo.getSql(), pojo.getDatasource());
                    String message = messageEngine.process(pojo.getTemplate(), result);
                    String subject = messageEngine.process(pojo.getSubject(), result);
                    List<File> attachments = new ArrayList<>(pojo.getAttachments().size());
                    for (EventMailAttachmentPojo attachmentPojo : pojo.getAttachments()) {
                        LOGGER.info("Execute attachment:{} {}", attachmentPojo.getId(), attachmentPojo.getDesc());
                        switch (attachmentPojo.getAttachmentType()) {
                            case XLSX: {
                                List<String[]> attachmentResult = CommonBaseDao.executeDataQuery(attachmentPojo.getSql(), attachmentPojo.getDatasource());
                                File file = File.createTempFile(attachmentPojo.getAttachmentName(), AttachmentType.XLSX.getSuffix());
                                ExportUtil.generateExcelFile(attachmentResult, file);
                                LOGGER.info("File:{} generated", file.getAbsolutePath());
                                attachments.add(file);
                            }
                            break;
                            case CSV: {
                                List<String[]> attachmentResult = CommonBaseDao.executeDataQuery(attachmentPojo.getSql(), attachmentPojo.getDatasource());
                                File file = File.createTempFile(attachmentPojo.getAttachmentName(), AttachmentType.CSV.getSuffix());
                                ExportUtil.generateCsvFile(attachmentResult, file);
                                LOGGER.info("File:{} generated", file.getAbsolutePath());
                                attachments.add(file);
                            }
                            break;
                            default:
                                throw new RuntimeException("Unsupported attachment type");
                        }
                    }
                    boolean success = MailNotify.send(subject, message, pojo.getTos(), pojo.getCcs(), attachments);
                    if (success) {
                        WechatNotify.notify("邮件任务:%s %s发送成功!", pojo.getId(), pojo.getDesc());
                    } else {
                        WechatNotify.notify("邮件任务:%s %s发送失败!", pojo.getId(), pojo.getDesc());
                    }
                }
            }
            LOGGER.info("Mail job:{} end", jobExecutionContext.getJobDetail().getKey().getName());
        } catch (Exception e) {
            LOGGER.error("Error in execute job", e);
            WechatNotify.notify("Mail job encounter error");
        }
    }
}
