package com.dstealer.hellobaby.notify;

import com.dstealer.hellobaby.common.Constant;
import com.dstealer.hellobaby.notify.support.MailConfig;
import com.dstealer.hellobaby.startup.EnvConfig;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 邮件通知
 * Created by LiShiwu on 02/18/2017.
 */
public class MailNotify {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailNotify.class);
    private static MailConfig mailConfig;

    /**
     * 初始化参数
     *
     * @param mailConfig
     */
    public synchronized static void init(MailConfig mailConfig) {
        MailNotify.mailConfig = mailConfig;
        LOGGER.info(Constant.ASTERISK_TEMPLATE, "Mail notify init complete");
    }

    /**
     * 发送email
     *
     * @param subject
     * @param message
     * @param tos     Wez Furlong<wez@example.com>,doe@example.com
     * @param ccs     Wez Furlong<wez@example.com>,doe@example.com
     * @param files
     * @return
     */
    public static boolean send(String subject, String message, String tos, String ccs, List<File> files) {
        try {
            List<InternetAddress> toAddresses = new ArrayList<>();
            if (tos != null && tos.length() > 0) {
                for (InternetAddress internetAddress : InternetAddress.parse(tos)) {
                    toAddresses.add(internetAddress);
                }
            }
            List<InternetAddress> ccAddresses = new ArrayList<>();
            if (ccs != null && ccs.length() > 0) {
                for (InternetAddress internetAddress : InternetAddress.parse(ccs)) {
                    ccAddresses.add(internetAddress);
                }
            }
            List<EmailAttachment> attachments = new ArrayList<>();
            if (files != null && files.size() > 0) {
                for (File file : files) {
                    EmailAttachment attachment = new EmailAttachment();
                    attachment.setName(file.getName());
                    attachment.setPath(file.getPath());
                    attachment.setDisposition(EmailAttachment.ATTACHMENT);
                    attachments.add(attachment);
                }
            }
            return send(subject, message, toAddresses, ccAddresses, attachments);
        } catch (Exception e) {
            LOGGER.error("Error in send email", e);
        }
        return false;
    }

    /**
     * 发送email
     *
     * @param subject
     * @param message
     * @param tos
     * @param ccs
     * @param attachments
     * @return
     */
    public static boolean send(String subject, String message, List<InternetAddress> tos, List<InternetAddress> ccs, List<EmailAttachment> attachments) {
        if (MailNotify.mailConfig == null) {
            LOGGER.warn("parameters has not init");
            return false;
        }
        if (subject == null || subject.length() == 0) {
            LOGGER.warn("subject cant't be empty");
            return false;
        }
        if (message == null || message.length() == 0) {
            LOGGER.warn("message can't be empty");
            return false;
        }
        if (tos == null || tos.isEmpty()) {
            LOGGER.warn("receiver can't be empty");
            return false;
        }
        try {
            LOGGER.info("Send email subject:{} message:{} to:{} cc:{}", new String[]{subject, message, printAddress(tos), printAddress(ccs)});
            if (EnvConfig.isMock) {
                return true;
            } else {
                MultiPartEmail email = new MultiPartEmail();
                email.setHostName(mailConfig.smtpHostName);
                email.setSmtpPort(mailConfig.smtpPort);
                email.setAuthenticator(new DefaultAuthenticator(mailConfig.userName, mailConfig.password));
                email.setFrom(mailConfig.userName);
                email.setSSLOnConnect(mailConfig.sslEnable);
                email.setSubject(subject);
                email.setMsg(message);
                email.setTo(tos);
                if (ccs != null && !ccs.isEmpty()) {
                    email.setCc(ccs);
                }
                if (attachments != null && !attachments.isEmpty()) {
                    for (EmailAttachment attachment : attachments) {
                        email.attach(attachment);
                    }
                }
                email.send();
                return true;
            }
        } catch (EmailException e) {
            LOGGER.error("Error in send mail", e);
            return false;
        }
    }

    /**
     * 打印InternetAddress
     *
     * @param addresses
     * @return
     */
    private static String printAddress(List<InternetAddress> addresses) {
        if (addresses != null) {
            StringBuilder builder = new StringBuilder();
            for (InternetAddress address : addresses) {
                builder.append(address.toString()).append(" ");
            }
            return builder.toString();
        } else {
            return "";
        }
    }
}
