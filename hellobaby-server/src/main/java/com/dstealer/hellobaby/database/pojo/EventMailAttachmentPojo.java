package com.dstealer.hellobaby.database.pojo;

import com.dstealer.hellobaby.database.common.RSHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by LiShiwu on 03/01/2017.
 */
public class EventMailAttachmentPojo {
    public static final RSHandler<EventMailAttachmentPojo> INSTANCE = new RSHandler<EventMailAttachmentPojo>() {
        @Override
        public EventMailAttachmentPojo parseFrom(ResultSet rs) throws SQLException {
            EventMailAttachmentPojo pojo = new EventMailAttachmentPojo();
            pojo.setId(rs.getInt("id"));
            pojo.setMailId(rs.getInt("mail_id"));
            pojo.setDatasource(rs.getString("datasource"));
            pojo.setSql(rs.getString("sql"));
            pojo.setAttachmentType(AttachmentType.valueOf(rs.getString("attachment_type")));
            pojo.setAttachmentName(rs.getString("attachment_name"));
            pojo.setDesc(rs.getString("desc"));
            return pojo;
        }
    };
    private int id;
    private int mailId;
    private String datasource;
    private String sql;
    private AttachmentType attachmentType;
    private String attachmentName;
    private String desc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMailId() {
        return mailId;
    }

    public void setMailId(int mailId) {
        this.mailId = mailId;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public AttachmentType getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(AttachmentType attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
