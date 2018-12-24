package com.dstealer.hellobaby.client.biz;

import org.hyperic.sigar.*;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * 远程Sigar服务接口
 * Created by LiShiwu on 05/24/2017.
 */
public interface RemoteSigarService extends Remote {
    long getPid() throws RemoteException;

    long getServicePid(String name) throws RemoteException;

    Mem getMem() throws RemoteException;

    Swap getSwap() throws RemoteException;

    Cpu getCpu() throws RemoteException;

    CpuPerc getCpuPerc() throws RemoteException;

    Uptime getUptime() throws RemoteException;

    ResourceLimit getResourceLimit() throws RemoteException;

    double[] getLoadAverage() throws RemoteException;

    long[] getProcList() throws RemoteException;

    ProcStat getProcStat() throws RemoteException;

    ProcMem getProcMem(long pid) throws RemoteException;

    ProcMem getProcMem(String pid) throws RemoteException;

    ProcMem getMultiProcMem(String query) throws RemoteException;

    ProcState getProcState(long pid) throws RemoteException;

    ProcState getProcState(String pid) throws RemoteException;

    ProcTime getProcTime(long pid) throws RemoteException;

    ProcTime getProcTime(String pid) throws RemoteException;

    ProcCpu getProcCpu(long pid) throws RemoteException;

    ProcCpu getProcCpu(String pid) throws RemoteException;

    MultiProcCpu getMultiProcCpu(String query) throws RemoteException;

    ProcCred getProcCred(long pid) throws RemoteException;

    ProcCred getProcCred(String pid) throws RemoteException;

    ProcCredName getProcCredName(long pid) throws RemoteException;

    ProcCredName getProcCredName(String pid) throws RemoteException;

    ProcFd getProcFd(long pid) throws RemoteException;

    ProcFd getProcFd(String pid) throws RemoteException;

    ProcExe getProcExe(long pid) throws RemoteException;

    ProcExe getProcExe(String pid) throws RemoteException;

    String[] getProcArgs(long pid) throws RemoteException;

    String[] getProcArgs(String pid) throws RemoteException;

    Map getProcEnv(long pid) throws RemoteException;

    Map getProcEnv(String pid) throws RemoteException;

    String getProcEnv(long pid, String key) throws RemoteException;

    String getProcEnv(String pid, String key) throws RemoteException;

    List getProcModules(long pid) throws RemoteException;

    List getProcModules(String pid) throws RemoteException;

    long getProcPort(int protocol, long port) throws RemoteException;

    long getProcPort(String protocol, String port) throws RemoteException;

    FileSystem[] getFileSystemList() throws RemoteException;

    FileSystemMap getFileSystemMap() throws RemoteException;

    FileSystemUsage getMountedFileSystemUsage(String name)
            throws RemoteException;

    FileSystemUsage getFileSystemUsage(String name)
            throws RemoteException;

    DiskUsage getDiskUsage(String name)
            throws RemoteException;

    FileInfo getFileInfo(String name) throws RemoteException;

    FileInfo getLinkInfo(String name) throws RemoteException;

    DirStat getDirStat(String name) throws RemoteException;

    DirUsage getDirUsage(String name) throws RemoteException;

    CpuInfo[] getCpuInfoList() throws RemoteException;

    Cpu[] getCpuList() throws RemoteException;

    CpuPerc[] getCpuPercList() throws RemoteException;

    NetRoute[] getNetRouteList() throws RemoteException;

    NetInterfaceConfig getNetInterfaceConfig(String name)
            throws RemoteException;

    NetInterfaceConfig getNetInterfaceConfig()
            throws RemoteException;

    NetInterfaceStat getNetInterfaceStat(String name)
            throws RemoteException;

    String[] getNetInterfaceList() throws RemoteException;

    NetConnection[] getNetConnectionList(int flags)
            throws RemoteException;

    String getNetListenAddress(long port)
            throws RemoteException;

    String getNetListenAddress(String port)
            throws RemoteException;

    NetStat getNetStat() throws RemoteException;

    String getNetServicesName(int protocol, long port) throws RemoteException;

    Who[] getWhoList() throws RemoteException;

    Tcp getTcp() throws RemoteException;

    NfsClientV2 getNfsClientV2() throws RemoteException;

    NfsServerV2 getNfsServerV2() throws RemoteException;

    NfsClientV3 getNfsClientV3() throws RemoteException;

    NfsServerV3 getNfsServerV3() throws RemoteException;

    NetInfo getNetInfo() throws RemoteException;

    String getFQDN() throws RemoteException;

    OperatingSystem getOperatingSystem() throws RemoteException;
}
