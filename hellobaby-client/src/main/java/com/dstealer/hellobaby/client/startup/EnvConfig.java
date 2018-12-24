package com.dstealer.hellobaby.client.startup;

import com.dstealer.hellobaby.client.biz.Constants;
import com.dstealer.hellobaby.client.common.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * 环境参数配置
 * Created by LiShiwu on 02/19/2017.
 */
public class EnvConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvConfig.class);
    private static final String CFG_NAME = "app.properties";
    /**
     * 测试模式标志条件
     */
    public static boolean isMock;
    /**
     * RMI绑定本地端口
     */
    public static int LOCAL_PORT = -1;
    public static String LOCAL_IP;

    static {
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        if (url != null && url.getPath().contains("test")) {
            isMock = true;
        }
    }

    /**
     * 初始化环境参数
     */
    public static void init() throws Exception {
        LOGGER.info("EnvConfig init start");
        PropsUtil util = PropsUtil.loadFromClassPath(CFG_NAME);
        LOCAL_PORT = util.getInt(Constants.KEY_LOCAL_PORT);
        LOGGER.info("RMI bind local port:{}", LOCAL_PORT);
        LOCAL_IP = util.getProperty(Constants.KEY_LOCAL_IP);
        LOGGER.info("RMI bind local ip:{}", LOCAL_IP);
        LOGGER.info("EnvConfig init complete");
    }
}
