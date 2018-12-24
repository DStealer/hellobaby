package com.dstealer.hellobaby.notify.support;

/**
 * 发送邮件参数配置
 * Created by LiShiwu on 02/19/2017.
 */
public class MailConfig {
    public transient String smtpHostName;
    public transient int smtpPort;
    public transient String userName;
    public transient String password;
    public transient boolean sslEnable = true;
}
