package com.dstealer.hellobaby.database.common;

import com.dstealer.hellobaby.common.ExportUtil;
import org.junit.Test;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by MTL on 2/19/2017.
 * Time: 17:04
 * Description:
 */
public class SQLExecutorTest {
    private static final String LOCATION_DESKTOP = System.getProperty("user.dir");

    @Test

    public void queryList() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1/testdb?serverTimezone=Asia/Shanghai&characterEncoding=utf-8", "user", "user");

        SQLExecutor sqlExecutor = new SQLExecutor(connection);
        SQLStatement.Builder sqlBuilder = SQLStatement.newBuilder();
        sqlBuilder.append("SELECT id '任务号',module '模块名称', `key` '关键字' ,`value` '值',`desc` '描述' FROM testdb.t_app_config limit 10");
        List<String[]> rsRowList = sqlExecutor.queryHeaderArrayList(sqlBuilder.build());
        connection.close();

        ExportUtil.generateExcelFile(rsRowList, new File(LOCATION_DESKTOP + "/data.xlsx"));
        ExportUtil.generateCsvFile(rsRowList, new File(LOCATION_DESKTOP + "/data.txt"));

    }

    @Test
    public void test() throws Exception {
        System.out.println(TimeZone.getDefault());
        System.out.println(LOCATION_DESKTOP);

    }
}