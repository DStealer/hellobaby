package com.dstealer.hellobaby.database.dao;

import com.dstealer.hellobaby.database.common.DSManager;
import com.dstealer.hellobaby.database.common.SQLExecutor;
import com.dstealer.hellobaby.database.common.SQLStatement;

import java.util.List;
import java.util.Map;

/**
 * Virtual数据源管理
 * Created by LiShiwu on 02/18/2017.
 */
public class CommonBaseDao {
    /**
     * 获取只读SQL执行器
     *
     * @return
     * @throws Exception
     */
    private static SQLExecutor getReadOnlyExecutor(String datasource) throws Exception {
        return new SQLExecutor(DSManager.SINGLETON.getReadonlyConnection(datasource));
    }

    /**
     * 执行查询
     *
     * @param sql
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> executeQuery(String sql, String datasource) throws Exception {
        SQLExecutor executor = null;
        try {
            executor = getReadOnlyExecutor(datasource);
            SQLStatement.Builder builder = SQLStatement.newBuilder();
            builder.append(sql);
            return executor.queryMapList(builder.build());
        } finally {
            if (executor != null) {
                executor.close();
            }
        }
    }

    /**
     * 查询数据，第一行为数据标题
     *
     * @param sql
     * @param datasource
     * @return
     * @throws Exception
     */
    public static List<String[]> executeDataQuery(String sql, String datasource) throws Exception {
        SQLExecutor executor = null;
        try {
            executor = getReadOnlyExecutor(datasource);
            SQLStatement.Builder builder = SQLStatement.newBuilder();
            builder.append(sql);
            return executor.queryHeaderArrayList(builder.build());
        } finally {
            if (executor != null) {
                executor.close();
            }
        }
    }
}
