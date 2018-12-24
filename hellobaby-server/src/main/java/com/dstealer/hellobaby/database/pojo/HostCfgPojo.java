package com.dstealer.hellobaby.database.pojo;

import com.dstealer.hellobaby.database.common.RSHandler;

/**
 * 主机服务配置信息
 * Created by LiShiwu on 05/28/2017.
 */
public class HostCfgPojo {
    public static final RSHandler<HostCfgPojo> HANDLER = rs -> {
        HostCfgPojo pojo = new HostCfgPojo();
        pojo.setId(rs.getInt("id"));
        pojo.setHostName(rs.getString("host_name"));
        pojo.setIp(rs.getString("ip"));
        pojo.setPort(rs.getInt("port"));
        pojo.setEnable(rs.getBoolean("enable"));
        pojo.setDesc(rs.getString("desc"));
        return pojo;
    };
    private int id;
    private String hostName;
    private String ip;
    private int port;
    private String desc;
    private boolean enable;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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

    @Override
    public String toString() {
        return "HostCfgPojo{" +
                "id=" + id +
                ", hostName='" + hostName + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", desc='" + desc + '\'' +
                ", enable=" + enable +
                '}';
    }
}
