package com.dstealer.hellobaby.notify;

import com.dstealer.hellobaby.common.CipherUtil;
import com.dstealer.hellobaby.notify.support.MailConfig;
import org.apache.commons.mail.*;
import org.junit.Assert;
import org.junit.Test;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiShiwu on 02/18/2017.
 */
public class MailNotifyTest {
    @Test
    public void test001() throws Exception {
        Email email = new SimpleEmail();
        email.setHostName("smtp.exmail.example.com");
        email.setSmtpPort(465);
        email.setSSLOnConnect(true);
        email.setAuthenticator(new DefaultAuthenticator("demo@example.com", ""));
        email.setFrom("demo@example.com");
        email.setSubject("TestMail");
        email.setMsg("This is a test mail ... :-)");
        email.addTo("demo@example.com", "LiShiwu");
        String rslt = email.send();
        System.out.println(rslt);
    }

    @Test
    public void test002() throws Exception {
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath("demo.txt");
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription("Picture of John");
        attachment.setName("John");

        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp.exmail.example.com");
        email.setSmtpPort(465);
        email.setSSLOnConnect(true);
        email.setAuthenticator(new DefaultAuthenticator("demo@example.com", ""));
        email.setFrom("demo@example.com");

        email.setSubject("TestMail");

        email.setMsg("This is a test mail ... :-) -- send by Susan");
        email.addTo("demo@example.com");
        email.addCc("demo@example.com");
        // add the attachment
        email.attach(attachment);
        // send the email
        email.send();
    }

    @Test
    public void test003() throws Exception {
        InternetAddress[] internetAddresses = InternetAddress.parse("Wez Furlong<wez@example.com>,doe@example.com");
        for (InternetAddress internetAddress : internetAddresses) {
            System.out.println(internetAddress.getPersonal() + "=" + internetAddress.getAddress());
        }
    }

    @Test
    public void test004() throws Exception {
        MailConfig mailConfig = new MailConfig();
        mailConfig.smtpHostName = "smtp.example.com";
        mailConfig.smtpPort = 465;
        mailConfig.sslEnable = true;
        mailConfig.userName = "demo@example.com";
        mailConfig.password = CipherUtil.decrypt("");
        MailNotify.init(mailConfig);
        List<File> files = new ArrayList<>();
        files.add(new File("hug.jpg"));
        files.add(new File("timg.jpg"));
        Assert.assertTrue(MailNotify.send("this is a testing", "您好,这里是测试", "demo@example.com", null, files));
    }
}