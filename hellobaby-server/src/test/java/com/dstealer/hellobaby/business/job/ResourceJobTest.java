package com.dstealer.hellobaby.business.job;

import com.dstealer.hellobaby.AppTest;
import org.junit.Test;

/**
 * Created by DStealer on 4/18/17.
 */
public class ResourceJobTest extends AppTest {
    @Test
    public void tt1() throws Exception {
        scheduleJob("resource_job", ResourceJob.class);
        System.in.read();
    }
}