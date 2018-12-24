package com.dstealer.hellobaby.database.common;

import com.dstealer.hellobaby.common.Constant;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * 数据库管理
 * Created by LiShiwu on 02/18/2017.
 */
public enum DBManager {
    SINGLETON;
    private static final Logger LOGGER = LoggerFactory.getLogger(DBManager.class);

    public void start() throws SQLException {
        Server server = Server.createTcpServer("-ifExists", "-tcpAllowOthers", "-baseDir", System.getProperty("user.dir"));
        server.start();
        LOGGER.info(Constant.ASTERISK_TEMPLATE, "Database init complete at:" + server.getURL());
    }
}
