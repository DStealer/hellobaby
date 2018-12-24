package com.dstealer.hellobaby.client.biz;

import com.dstealer.hellobaby.client.common.SingletonManager;
import com.dstealer.hellobaby.client.startup.EnvConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * 服务管理
 * Created by LiShiwu on 05/28/2017.
 */
public class ServiceManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceManager.class);

    /**
     * 启动RMI服务
     *
     * @throws RemoteException
     * @throws AlreadyBoundException
     * @throws NotBoundException
     */
    public static void init() throws RemoteException, AlreadyBoundException, NotBoundException {
        LOGGER.info("Service init start...");
        System.setProperty("java.rmi.server.hostname", EnvConfig.LOCAL_IP);
        Registry registry = LocateRegistry.createRegistry(EnvConfig.LOCAL_PORT);
        registry.bind(RemoteSigarService.class.getSimpleName(), locateLocal());
        for (String service : registry.list()) {
            Remote remoteService = registry.lookup(service);
            LOGGER.info("{} => {}", service, remoteService.toString());
        }
        LOGGER.info("Service init end...");
    }

    /**
     * 获取远程Sigar服务
     *
     * @param ip
     * @param port
     * @return
     * @throws
     */
    public static RemoteSigarService locate(String ip, int port) throws RemoteException, NotBoundException {
        LOGGER.info("Locate remote sigar service from:{} port:{}", ip, port);
        Registry registry = LocateRegistry.getRegistry(ip, port);
        return (RemoteSigarService) registry.lookup(RemoteSigarService.class.getSimpleName());
    }

    /**
     * 获取本地Sigar服务
     *
     * @return
     */
    public static RemoteSigarService locateLocal() throws RemoteException {
        LOGGER.info("Locate sigar service from localhost");
        return SingletonManager.get(RemoteSigarServiceImpl.class);
    }
}
