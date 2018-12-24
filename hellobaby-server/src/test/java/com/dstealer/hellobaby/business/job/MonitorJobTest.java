package com.dstealer.hellobaby.business.job;

import com.dstealer.hellobaby.AppTest;
import org.junit.Test;

/**
 * Created by LiShiwu on 04/13/2017.
 */
public class MonitorJobTest extends AppTest {
    @Test
    public void test001() throws Exception {
        scheduleJob("task_monitor_job_10X60", MonitorJob.class);
        System.in.read();
    }
}