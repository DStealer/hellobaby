package com.dstealer.hellobaby.business.schedule;

import com.dstealer.hellobaby.common.Constant;
import com.dstealer.hellobaby.database.dao.SystemBaseDao;
import com.dstealer.hellobaby.database.pojo.JobConfigPojo;
import com.dstealer.hellobaby.template.MessageEngine;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

/**
 * Created by LiShiwu on 02/18/2017.
 */
public class JobScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobScheduler.class);

    /**
     * 开启任务调度,应该仅执行一次
     *
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static void start() throws Exception {
        LOGGER.info(Constant.ASTERISK_TEMPLATE, "Scheduler initialize start");
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();
        scheduler.getContext().put(Constant.KEY_MESSAGE_ENGINE, new MessageEngine());
        List<JobConfigPojo> pojos = SystemBaseDao.getJobConfig();
        for (JobConfigPojo pojo : pojos) {
            if (pojo.isEnable()) {
                JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(pojo.getClazz())).withIdentity(pojo.getName()).build();
                CronTrigger jobTrigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(pojo.getCornExpression())).build();
                Date firsRuntDatetime = scheduler.scheduleJob(jobDetail, jobTrigger);
                LOGGER.info("Job:{} start at:{}", jobDetail.getKey().getName(), firsRuntDatetime);
            } else {
                LOGGER.warn("Job:{} is disable", pojo.getName());
            }
        }
        scheduler.start();
        LOGGER.info(Constant.ASTERISK_TEMPLATE, "Scheduler init complete!");
    }
}
