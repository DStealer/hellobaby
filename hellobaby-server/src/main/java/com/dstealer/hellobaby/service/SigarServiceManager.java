package com.dstealer.hellobaby.service;

import com.dstealer.hellobaby.client.biz.RemoteSigarService;
import com.dstealer.hellobaby.client.biz.ServiceManager;

/**
 * Sigar服务管理
 * Created by LiShiwu on 05/28/2017.
 */
public class SigarServiceManager {
    /**
     * 获取Sigar服务
     *
     * @param ip
     * @param port
     * @return
     * @throws Exception
     */
    public static RemoteSigarService get(String ip, int port) throws Exception {
        if ("127.0.0.1".equals(ip) || "localhost".equals(ip)) {
            return ServiceManager.locateLocal();
        } else {
            return ServiceManager.locate(ip, port);
        }
    }
}
