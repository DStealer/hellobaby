package com.dstealer.hellobaby.database.dao;

import com.dstealer.hellobaby.AppTest;
import com.dstealer.hellobaby.database.pojo.EventMonitorPojo;
import org.junit.Test;

import java.util.List;

/**
 * Created by LiShiwu on 02/18/2017.
 */
public class EventMonitorDaoTest extends AppTest {
    @Test
    public void test001() throws Exception {
        List<EventMonitorPojo> pojos = SystemBaseDao.getEventMonitorByJobName("task_monitor_job_10X60");
        for (EventMonitorPojo pojo : pojos) {
            System.out.println(printMessage(pojo));
        }
    }
}