package com.dstealer.hellobaby.client.biz;

import com.dstealer.hellobaby.client.common.CommUtil;
import com.dstealer.hellobaby.client.common.SingletonFactory;
import org.hyperic.sigar.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

/**
 * 服务提供者
 * Created by LiShiwu on 05/24/2017.
 */
public class RemoteSigarServiceImpl extends UnicastRemoteObject implements RemoteSigarService, SingletonFactory<RemoteSigarServiceImpl> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteSigarServiceImpl.class);

    private SigarProxy proxy;

    public RemoteSigarServiceImpl() throws RemoteException {
        this.proxy = SigarProxyCache.newInstance();
    }

    /**
     * 加载本地类库
     */
    public static void loadNativeLib() throws IOException {
        //加载依赖类库
        String osName = System.getProperty("os.name").toLowerCase();
        String libname;
        if (osName.startsWith("window")) {
            libname = "sigar-amd64-winnt.dll";
        } else if (osName.startsWith("linux")) {
            libname = "libsigar-amd64-linux.so";
        } else {
            throw new RuntimeException("Cant't decide os type");
        }
        LOGGER.info("Load lib{}", libname);
        File libFile = CommUtil.explodeToTemporaryFile(libname);
        System.load(libFile.getAbsolutePath());
    }

    @Override
    public long getPid() throws RemoteException {
        return proxy.getPid();
    }

    @Override
    public long getServicePid(String name) throws RemoteException {
        try {
            return proxy.getServicePid(name);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public Mem getMem() throws RemoteException {
        try {
            return proxy.getMem();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public Swap getSwap() throws RemoteException {
        try {
            return proxy.getSwap();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public Cpu getCpu() throws RemoteException {
        try {
            return proxy.getCpu();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public CpuPerc getCpuPerc() throws RemoteException {
        try {
            return proxy.getCpuPerc();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public Uptime getUptime() throws RemoteException {
        try {
            return proxy.getUptime();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ResourceLimit getResourceLimit() throws RemoteException {
        try {
            return proxy.getResourceLimit();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public double[] getLoadAverage() throws RemoteException {
        try {
            return proxy.getLoadAverage();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public long[] getProcList() throws RemoteException {
        try {
            return proxy.getProcList();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ProcStat getProcStat() throws RemoteException {
        try {
            return proxy.getProcStat();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ProcMem getProcMem(long pid) throws RemoteException {
        try {
            return proxy.getProcMem(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ProcMem getProcMem(String pid) throws RemoteException {
        try {
            return proxy.getProcMem(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ProcMem getMultiProcMem(String query) throws RemoteException {
        try {
            return proxy.getMultiProcMem(query);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ProcState getProcState(long pid) throws RemoteException {
        try {
            return proxy.getProcState(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ProcState getProcState(String pid) throws RemoteException {
        try {
            return proxy.getProcState(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ProcTime getProcTime(long pid) throws RemoteException {
        try {
            return proxy.getProcTime(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ProcTime getProcTime(String pid) throws RemoteException {
        try {
            return proxy.getProcTime(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ProcCpu getProcCpu(long pid) throws RemoteException {
        try {
            return proxy.getProcCpu(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ProcCpu getProcCpu(String pid) throws RemoteException {
        try {
            return proxy.getProcCpu(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public MultiProcCpu getMultiProcCpu(String query) throws RemoteException {
        try {
            return proxy.getMultiProcCpu(query);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ProcCred getProcCred(long pid) throws RemoteException {
        try {
            return proxy.getProcCred(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ProcCred getProcCred(String pid) throws RemoteException {
        try {
            return proxy.getProcCred(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ProcCredName getProcCredName(long pid) throws RemoteException {
        try {
            return proxy.getProcCredName(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ProcCredName getProcCredName(String pid) throws RemoteException {
        try {
            return proxy.getProcCredName(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ProcFd getProcFd(long pid) throws RemoteException {
        try {
            return proxy.getProcFd(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ProcFd getProcFd(String pid) throws RemoteException {
        try {
            return proxy.getProcFd(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ProcExe getProcExe(long pid) throws RemoteException {
        try {
            return proxy.getProcExe(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public ProcExe getProcExe(String pid) throws RemoteException {
        try {
            return proxy.getProcExe(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public String[] getProcArgs(long pid) throws RemoteException {
        try {
            return proxy.getProcArgs(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public String[] getProcArgs(String pid) throws RemoteException {
        try {
            return proxy.getProcArgs(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public Map getProcEnv(long pid) throws RemoteException {
        try {
            return proxy.getProcEnv(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public Map getProcEnv(String pid) throws RemoteException {
        try {
            return proxy.getProcEnv(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public String getProcEnv(long pid, String key) throws RemoteException {
        try {
            return proxy.getProcEnv(pid, key);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public String getProcEnv(String pid, String key) throws RemoteException {
        try {
            return proxy.getProcEnv(pid, key);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public List getProcModules(long pid) throws RemoteException {
        try {
            return proxy.getProcModules(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public List getProcModules(String pid) throws RemoteException {
        try {
            return proxy.getProcModules(pid);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public long getProcPort(int protocol, long port) throws RemoteException {
        try {
            return proxy.getProcPort(protocol, port);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public long getProcPort(String protocol, String port) throws RemoteException {
        try {
            return proxy.getProcPort(protocol, port);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public FileSystem[] getFileSystemList() throws RemoteException {
        try {
            return proxy.getFileSystemList();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public FileSystemMap getFileSystemMap() throws RemoteException {
        try {
            return proxy.getFileSystemMap();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public FileSystemUsage getMountedFileSystemUsage(String name) throws RemoteException {
        try {
            return proxy.getMountedFileSystemUsage(name);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public FileSystemUsage getFileSystemUsage(String name) throws RemoteException {
        try {
            return proxy.getFileSystemUsage(name);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public DiskUsage getDiskUsage(String name) throws RemoteException {
        try {
            return proxy.getDiskUsage(name);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public FileInfo getFileInfo(String name) throws RemoteException {
        try {
            return proxy.getFileInfo(name);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public FileInfo getLinkInfo(String name) throws RemoteException {
        try {
            return proxy.getLinkInfo(name);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public DirStat getDirStat(String name) throws RemoteException {
        try {
            return proxy.getDirStat(name);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public DirUsage getDirUsage(String name) throws RemoteException {
        try {
            return proxy.getDirUsage(name);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public CpuInfo[] getCpuInfoList() throws RemoteException {
        try {
            return proxy.getCpuInfoList();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public Cpu[] getCpuList() throws RemoteException {
        try {
            return proxy.getCpuList();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public CpuPerc[] getCpuPercList() throws RemoteException {
        try {
            return proxy.getCpuPercList();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public NetRoute[] getNetRouteList() throws RemoteException {
        try {
            return proxy.getNetRouteList();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public NetInterfaceConfig getNetInterfaceConfig(String name) throws RemoteException {
        try {
            return proxy.getNetInterfaceConfig(name);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public NetInterfaceConfig getNetInterfaceConfig() throws RemoteException {
        try {
            return proxy.getNetInterfaceConfig();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public NetInterfaceStat getNetInterfaceStat(String name) throws RemoteException {
        try {
            return proxy.getNetInterfaceStat(name);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public String[] getNetInterfaceList() throws RemoteException {
        try {
            return proxy.getNetInterfaceList();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public NetConnection[] getNetConnectionList(int flags) throws RemoteException {
        try {
            return proxy.getNetConnectionList(flags);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public String getNetListenAddress(long port) throws RemoteException {
        try {
            return proxy.getNetListenAddress(port);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public String getNetListenAddress(String port) throws RemoteException {
        try {
            return proxy.getNetListenAddress(port);
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public NetStat getNetStat() throws RemoteException {
        try {
            return proxy.getNetStat();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public String getNetServicesName(int protocol, long port) throws RemoteException {
        try {
            return proxy.getNetServicesName(protocol, port);
        } catch (Exception e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public Who[] getWhoList() throws RemoteException {
        try {
            return proxy.getWhoList();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public Tcp getTcp() throws RemoteException {
        try {
            return proxy.getTcp();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public NfsClientV2 getNfsClientV2() throws RemoteException {
        try {
            return proxy.getNfsClientV2();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public NfsServerV2 getNfsServerV2() throws RemoteException {
        try {
            return proxy.getNfsServerV2();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public NfsClientV3 getNfsClientV3() throws RemoteException {
        try {
            return proxy.getNfsClientV3();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public NfsServerV3 getNfsServerV3() throws RemoteException {
        try {
            return proxy.getNfsServerV3();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public NetInfo getNetInfo() throws RemoteException {
        try {
            return proxy.getNetInfo();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public String getFQDN() throws RemoteException {
        try {
            return proxy.getFQDN();
        } catch (SigarException e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public OperatingSystem getOperatingSystem() throws RemoteException {
        try {
            return OperatingSystem.getInstance();
        } catch (Exception e) {
            LOGGER.warn("Error in invoke sigar service", e);
            throw new RemoteException(e.getMessage(), e);
        }
    }

    @Override
    public RemoteSigarServiceImpl create() throws Exception {
        loadNativeLib();
        return new RemoteSigarServiceImpl();
    }
}
