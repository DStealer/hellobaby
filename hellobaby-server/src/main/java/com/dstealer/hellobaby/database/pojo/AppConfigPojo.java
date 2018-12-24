package com.dstealer.hellobaby.database.pojo;

import com.dstealer.hellobaby.database.common.RSHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by LiShiwu on 02/18/2017.
 */
public class AppConfigPojo {

    public static final RSHandler<AppConfigPojo> INSTANCE = new RSHandler<AppConfigPojo>() {
        @Override
        public AppConfigPojo parseFrom(ResultSet rs) throws SQLException {
            AppConfigPojo appConfigPojo = new AppConfigPojo();
            appConfigPojo.setId(rs.getInt("id"));
            appConfigPojo.setModule(rs.getString("module"));
            appConfigPojo.setKey(rs.getString("key"));
            appConfigPojo.setValue(rs.getString("value"));
            appConfigPojo.setDesc(rs.getString("desc"));
            return appConfigPojo;
        }
    };
    private int id;
    private String module;
    private String key;
    private String value;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String description) {
        this.desc = desc;
    }
}
