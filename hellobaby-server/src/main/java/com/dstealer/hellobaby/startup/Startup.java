package com.dstealer.hellobaby.startup;


import com.dstealer.hellobaby.common.Constant;
import com.dstealer.hellobaby.database.common.DBManager;
import com.dstealer.hellobaby.database.common.DSManager;
import com.dstealer.hellobaby.business.schedule.JobScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 程序入口
 * Created by LiShiwu on 02/18/2017.
 */
public class Startup {
    private static final Logger LOGGER = LoggerFactory.getLogger(Startup.class);

    public static void main(String[] args) {
        try {
            LOGGER.info(Constant.ASTERISK_TEMPLATE, "Application starting in " + (EnvConfig.isMock ? "mock" : "online") + " mode");
            //数据库初始化
            DBManager.SINGLETON.start();
            //初始化数据源
            DSManager.SINGLETON.init();
            //初始化环境参数
            EnvConfig.init();
            //调度器执行
            JobScheduler.start();
            LOGGER.info(Constant.ASTERISK_TEMPLATE, "Application start complete");
        } catch (Exception e) {
            LOGGER.error("Application start error", e);
            System.exit(-1);
        }
    }
}
