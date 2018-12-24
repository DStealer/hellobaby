package com.dstealer.hellobaby.startup;

import com.dstealer.hellobaby.common.CipherUtil;
import com.dstealer.hellobaby.common.Constant;
import com.dstealer.hellobaby.notify.MailNotify;
import com.dstealer.hellobaby.notify.WechatNotify;
import com.dstealer.hellobaby.database.dao.AppDao;
import com.dstealer.hellobaby.notify.support.MailConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by LiShiwu on 02/19/2017.
 */
public class EnvConfig {
    public static final String moduleName = "Susan";
    private static final Logger LOGGER = LoggerFactory.getLogger(EnvConfig.class);
    /**
     * 测试模式标志条件
     */
    public static boolean isMock;

    static {
        String currentPath = EnvConfig.class.getResource("").toString();
        if (currentPath != null
                && (currentPath.toLowerCase().contains("test") || currentPath.toUpperCase().contains("target"))) {
            isMock = true;
        }
    }

    /**
     * 初始化环境参数
     */
    public static void init() throws Exception {
        LOGGER.info(Constant.ASTERISK_TEMPLATE, "EnvConfig init start");
        MailConfig mailConfig = new MailConfig();
        mailConfig.smtpHostName = AppDao.getString("mail", "host_name");
        mailConfig.smtpPort = AppDao.getNumber("mail", "smtp_port").intValue();
        mailConfig.userName = AppDao.getString("mail", "user_name");
        mailConfig.password = CipherUtil.decrypt(AppDao.getString("mail", "encrypt_password"));
        MailNotify.init(mailConfig);
        LOGGER.info(Constant.ASTERISK_TEMPLATE, "EnvConfig init complete");
    }
}
