package com.dstealer.hellobaby.database.pojo;

import com.dstealer.hellobaby.database.common.RSHandler;
import com.dstealer.hellobaby.notify.NotifyType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by LiShiwu on 02/18/2017.
 */
public class EventMonitorPojo {
    public static final RSHandler<EventMonitorPojo> INSTANCE = new RSHandler<EventMonitorPojo>() {
        @Override
        public EventMonitorPojo parseFrom(ResultSet rs) throws SQLException {
            EventMonitorPojo pojo = new EventMonitorPojo();
            pojo.setId(rs.getInt("id"));
            pojo.setModule(rs.getString("module"));
            pojo.setDatasource(rs.getString("datasource"));
            pojo.setSql(rs.getString("sql"));
            pojo.setNotifyType(NotifyType.valueOf(rs.getString("notify_type")));
            pojo.setTemplate(rs.getString("template"));
            pojo.setEnable(rs.getBoolean("enable"));
            pojo.setDesc(rs.getString("desc"));
            return pojo;
        }
    };
    private int id;
    private String module;
    private String datasource;
    private String sql;
    private NotifyType notifyType;
    private String template;
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

    public NotifyType getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(NotifyType notifyType) {
        this.notifyType = notifyType;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
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
