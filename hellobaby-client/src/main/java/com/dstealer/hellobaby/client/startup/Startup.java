package com.dstealer.hellobaby.client.startup;


import com.dstealer.hellobaby.client.biz.ServiceManager;
import org.apache.log4j.Logger;

/**
 * 程序入口
 *
 * @author LiShiwu
 */
public class Startup {
    private static final Logger LOGGER = Logger.getLogger(Startup.class);

    public static void main(String[] args) {
        try {
            LOGGER.info("Application starting in {" + (EnvConfig.isMock ? "mock" : "online") + "} mode");
            EnvConfig.init();
            ServiceManager.init();
            LOGGER.info("Application start complete...");
        } catch (Exception e) {
            LOGGER.error("Application start error", e);
        }
    }
}
