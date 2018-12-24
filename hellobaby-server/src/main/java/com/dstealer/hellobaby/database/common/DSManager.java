package com.dstealer.hellobaby.database.common;

import com.dstealer.hellobaby.common.CipherUtil;
import com.dstealer.hellobaby.common.Constant;
import com.dstealer.hellobaby.database.dao.SystemBaseDao;
import com.dstealer.hellobaby.database.pojo.DatasourcePojo;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by LiShiwu on 02/17/2017.
 */
public enum DSManager {
    SINGLETON;
    private static final Logger LOGGER = LoggerFactory.getLogger(DSManager.class);
    private static final Map<String, PooledDataSource> MULTIPLE_PDS = new ConcurrentHashMap<>();

    /**
     * 获取事务连接
     *
     * @param datasourceName
     * @return
     * @throws SQLException
     */
    public Connection getTransactionConnection(String datasourceName) throws SQLException {
        Connection connection = MULTIPLE_PDS.get(datasourceName).getConnection();
        connection.setReadOnly(false);
        connection.setAutoCommit(false);
        return connection;
    }

    /**
     * 关闭所有连接
     */
    public void destroyAll() throws SQLException {
        for (PooledDataSource pds : this.MULTIPLE_PDS.values()) {
            pds.close();
        }
        MULTIPLE_PDS.clear();
    }

    /**
     * 关闭指定连接
     *
     * @param datasourceName
     */
    public void destroy(String datasourceName) throws SQLException {
        PooledDataSource pds = MULTIPLE_PDS.remove(datasourceName);
        if (pds != null) {
            pds.close();
        }
    }

    /**
     * 添加指定连接
     *
     * @param key
     * @param pooledDataSource
     */
    public void add(String key, PooledDataSource pooledDataSource) throws SQLException {
        if (!MULTIPLE_PDS.containsKey(key)) {
            MULTIPLE_PDS.put(key, pooledDataSource);
        }
    }

    /**
     * 获取只读连接
     *
     * @param datasourceName
     * @return
     * @throws SQLException
     */
    public Connection getReadonlyConnection(String datasourceName) throws SQLException {
        Connection connection = MULTIPLE_PDS.get(datasourceName).getConnection();
        connection.setReadOnly(true);
        return connection;
    }

    /**
     * 初始化系统数据源同时初始化其他数据源
     */
    public synchronized void init() throws Exception {
        if (MULTIPLE_PDS.isEmpty()) {
            MULTIPLE_PDS.put(SystemBaseDao.SYSTEM_DATASOURCE, new ComboPooledDataSource(SystemBaseDao.SYSTEM_DATASOURCE));
            LOGGER.info(Constant.ASTERISK_TEMPLATE, "Initialize datasource[" + SystemBaseDao.SYSTEM_DATASOURCE + "] completely");
            List<DatasourcePojo> pojos = SystemBaseDao.getDatasource();
            for (DatasourcePojo pojo : pojos) {
                ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
                comboPooledDataSource.setJdbcUrl(pojo.getUrl());
                comboPooledDataSource.setUser(pojo.getUsername());
                comboPooledDataSource.setPassword(CipherUtil.decrypt(pojo.getEncryptPassword()));
                if (pojo.getProperties() != null && pojo.getProperties().length() > 0) {
                    Properties properties = new Properties();
                    properties.load(new StringReader(pojo.getProperties()));
                    comboPooledDataSource.setProperties(properties);
                }
                MULTIPLE_PDS.put(pojo.getDatasource(), comboPooledDataSource);
                LOGGER.info(Constant.ASTERISK_TEMPLATE, "Initialize datasource[" + pojo.getDatasource() + "] completely");
            }
            LOGGER.info(Constant.ASTERISK_TEMPLATE, "Initialize datasource completely");
        } else {
            LOGGER.warn(Constant.ASTERISK_TEMPLATE, "Datasource has already initialized");
        }
    }
}
