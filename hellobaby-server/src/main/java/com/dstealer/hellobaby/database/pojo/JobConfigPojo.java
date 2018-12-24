package com.dstealer.hellobaby.database.pojo;

import com.dstealer.hellobaby.database.common.RSHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by LiShiwu on 04/12/2017.
 */
public class JobConfigPojo {
    public static final RSHandler<JobConfigPojo> INSTANCE = new RSHandler<JobConfigPojo>() {
        @Override
        public JobConfigPojo parseFrom(ResultSet rs) throws SQLException {
            JobConfigPojo pojo = new JobConfigPojo();
            pojo.setId(rs.getInt("id"));
            pojo.setName(rs.getString("name"));
            pojo.setClazz(rs.getString("class"));
            pojo.setCornExpression(rs.getString("corn_expression"));
            pojo.setEnable(rs.getBoolean("enable"));
            return pojo;
        }
    };
    private int id;
    private String name;
    private String clazz;
    private String CornExpression;
    private String desc;
    private boolean enable;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getCornExpression() {
        return CornExpression;
    }

    public void setCornExpression(String cornExpression) {
        CornExpression = cornExpression;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
