package com.dstealer.hellobaby.database;

import com.dstealer.hellobaby.AppTest;
import com.dstealer.hellobaby.database.common.DSManager;
import com.dstealer.hellobaby.database.common.SQLExecutor;
import com.dstealer.hellobaby.database.common.SQLStatement;
import com.dstealer.hellobaby.database.dao.SystemBaseDao;
import org.junit.Test;

import java.util.Map;

/**
 * Created by LiShiwu on 02/18/2017.
 */
public class DSManagerTest extends AppTest {
    @Test
    public void test001() throws Exception {
        SQLStatement.Builder builder = SQLStatement.newBuilder();
        builder.append("SELECT * FROM t_bak").limitOne();
        SQLExecutor executor = new SQLExecutor(DSManager.SINGLETON.getReadonlyConnection("ds_zero"));
        try {
            Map<String, String> rslt = executor.queryMapList(builder.build()).get(0);
            System.out.println(rslt);
        } finally {
            executor.close();
        }
    }

    @Test
    public void test002() throws Exception {
        SQLStatement.Builder builder = SQLStatement.newBuilder();
        builder.append("SELECT * FROM TEST").limitOne();
        SQLExecutor executor = new SQLExecutor(DSManager.SINGLETON.getReadonlyConnection(SystemBaseDao.SYSTEM_DATASOURCE));
        try {
            Map<String, String> rslt = executor.queryMapList(builder.build()).get(0);
            System.out.println(rslt);
        } finally {
            executor.close();
        }
    }
}