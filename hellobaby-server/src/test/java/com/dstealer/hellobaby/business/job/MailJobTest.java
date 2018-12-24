package com.dstealer.hellobaby.business.job;

import com.dstealer.hellobaby.AppTest;
import org.junit.Test;

/**
 * Created by LiShiwu on 02/26/2017.
 */
public class MailJobTest extends AppTest {
    @Test
    public void test001() throws Exception {
        scheduleJob("mail_balance_job", MailJob.class);
        System.in.read();
    }

    @Test
    public void test002() throws Exception {
        scheduleJob("mail_fund_data_job", MailJob.class);
        System.in.read();
    }

    @Test
    public void test003() throws Exception {
        scheduleJob("mail_tangyang_data_job", MailJob.class);
        System.in.read();
    }

    @Test
    public void test004() throws Exception {
        scheduleJob("mail_xjlc_snapshot_job", MailJob.class);
        System.in.read();
    }
}