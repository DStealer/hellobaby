package com.dstealer.hellobaby.database.dao;

import com.dstealer.hellobaby.database.common.SQLExecutor;
import com.dstealer.hellobaby.database.common.SQLStatement;
import com.dstealer.hellobaby.database.pojo.AppConfigPojo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 系统配置表管理
 * Created by LiShiwu on 02/18/2017.
 */
public class AppDao extends SystemBaseDao {
    /**
     * 获取获取字符串格式的数据
     *
     * @param module
     * @param key
     * @return
     * @throws Exception
     */
    public static String getString(String module, String key) throws Exception {
        SQLExecutor executor = getReadOnlyExecutor();
        try {
            SQLStatement.Builder builder = SQLStatement.newBuilder();
            builder.append("SELECT * FROM t_app_config WHERE `module` = ? AND `key` = ?", module, key);
            AppConfigPojo pojo = executor.queryOne(builder.build(), AppConfigPojo.INSTANCE);
            return pojo.getValue();
        } finally {
            executor.close();
        }
    }

    /**
     * 获取数字格式的数据
     *
     * @param module
     * @param key
     * @return
     * @throws Exception
     */
    public static Number getNumber(String module, String key) throws Exception {
        return new BigDecimal(getString(module, key));
    }

    /**
     * SQL执行结果
     *
     * @param module
     * @param key
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> getSQLResult(String module, String key, Object... params) throws Exception {
        SQLExecutor executor = getReadOnlyExecutor();
        try {
            SQLStatement.Builder builder = SQLStatement.newBuilder();
            builder.append(getString(module, key), params);
            return executor.queryMapList(builder.build());
        } finally {
            executor.close();
        }
    }
}
