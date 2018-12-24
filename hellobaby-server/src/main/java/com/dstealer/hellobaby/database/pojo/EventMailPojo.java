package com.dstealer.hellobaby.database.pojo;

import com.dstealer.hellobaby.database.common.RSHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by LiShiwu on 02/18/2017.
 */
public class EventMailPojo {
    public static final RSHandler<EventMailPojo> INSTANCE = new RSHandler<EventMailPojo>() {
        @Override
        public EventMailPojo parseFrom(ResultSet rs) throws SQLException {
            EventMailPojo pojo = new EventMailPojo();
            pojo.setId(rs.getInt("id"));
            pojo.setModule(rs.getString("module"));
            pojo.setDatasource(rs.getString("datasource"));
            pojo.setSql(rs.getString("sql"));
            pojo.setSubject(rs.getString("subject"));
            pojo.setTemplate(rs.getString("template"));
            pojo.setTos(rs.getString("tos"));
            pojo.setCcs(rs.getString("ccs"));
            pojo.setEnable(rs.getBoolean("enable"));
            pojo.setDesc(rs.getString("desc"));
            return pojo;
        }

    };
    private int id;
    private String module;
    private String datasource;
    private String sql;
    private String subject;
    private String template;
    private String tos;
    private String ccs;
    private List<EventMailAttachmentPojo> attachments;
    private boolean enable;
    private String desc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTos() {
        return tos;
    }

    public void setTos(String tos) {
        this.tos = tos;
    }

    public String getCcs() {
        return ccs;
    }

    public void setCcs(String ccs) {
        this.ccs = ccs;
    }

    public List<EventMailAttachmentPojo> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<EventMailAttachmentPojo> attachments) {
        this.attachments = attachments;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
