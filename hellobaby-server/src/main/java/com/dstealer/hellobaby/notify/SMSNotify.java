package com.dstealer.hellobaby.notify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 短信通知
 * Created by LiShiwu on 02/18/2017.
 */
public class SMSNotify {
    private static final Logger LOGGER = LoggerFactory.getLogger(SMSNotify.class);

    /**
     * 短信通知
     *
     * @param message
     * @return
     */
    public static boolean notify(String message) {
        LOGGER.info("Sent notice:{}", message);
        return false;
    }
}
