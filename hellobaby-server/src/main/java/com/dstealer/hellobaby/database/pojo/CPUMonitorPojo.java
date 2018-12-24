package com.dstealer.hellobaby.database.pojo;

import com.dstealer.hellobaby.database.common.RSHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by LiShiwu on 05/03/2017.
 */
public class CPUMonitorPojo {
    /**
     * 解析映射
     */
    public static final RSHandler<CPUMonitorPojo> HANDLER = new RSHandler<CPUMonitorPojo>() {
        @Override
        public CPUMonitorPojo parseFrom(ResultSet rs) throws SQLException {
            CPUMonitorPojo pojo = new CPUMonitorPojo();
            pojo.setId(rs.getInt("id"));
            pojo.setJobName(rs.getString("job_name"));
            pojo.setHostName(rs.getString("host_name"));
            pojo.setTemplate(rs.getString("template"));
            pojo.setThreshold(rs.getDouble("threshold"));
            pojo.setEnable(rs.getBoolean("enable"));
            pojo.setDesc(rs.getString("desc"));
            return pojo;
        }
    };
    private int id;
    private String jobName;
    private String hostName;
    private String template;
    private double threshold;
    private boolean enable;
    private String desc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
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
