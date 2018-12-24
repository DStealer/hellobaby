package com.dstealer.hellobaby.database.pojo;

import com.dstealer.hellobaby.database.common.RSHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by LiShiwu on 02/26/2017.
 */
public class DatasourcePojo {
    public static final RSHandler<DatasourcePojo> INSTANCE = new RSHandler<DatasourcePojo>() {
        @Override
        public DatasourcePojo parseFrom(ResultSet rs) throws SQLException {
            DatasourcePojo pojo = new DatasourcePojo();
            pojo.setId(rs.getInt("id"));
            pojo.setModule(rs.getString("module"));
            pojo.setDatasource(rs.getString("datasource"));
            pojo.setUrl(rs.getString("url"));
            pojo.setUsername(rs.getString("username"));
            pojo.setEncryptPassword(rs.getString("encrypt_password"));
            pojo.setProperties(rs.getString("properties"));
            pojo.setDesc(rs.getString("desc"));
            return pojo;
        }
    };
    private int id;
    private String module;
    private String datasource;
    private String url;
    private String username;
    private String encryptPassword;
    private String properties;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptPassword() {
        return encryptPassword;
    }

    public void setEncryptPassword(String encryptPassword) {
        this.encryptPassword = encryptPassword;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
