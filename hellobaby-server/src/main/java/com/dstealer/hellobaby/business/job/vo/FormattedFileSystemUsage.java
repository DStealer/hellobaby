package com.dstealer.hellobaby.business.job.vo;

import com.dstealer.hellobaby.common.CommUtil;
import com.dstealer.hellobaby.database.pojo.DirMonitorPojo;
import org.hyperic.sigar.FileSystemUsage;

/**
 * 格式化FileSystemUsage对象
 */
public class FormattedFileSystemUsage {
    private String dirName;
    private String thresholdPercent;
    private String avail;
    private long files;
    private long freeFiles;
    private long diskReads;
    private long diskWrites;
    private String diskReadBytes;
    private String diskWriteBytes;
    private double diskQueue;
    private double diskServiceTime;
    private String usePercent;
    private String total;
    private String free;
    private String used;

    public FormattedFileSystemUsage(DirMonitorPojo pojo, FileSystemUsage usage) {
        this.dirName = pojo.getDirName();
        this.thresholdPercent = CommUtil.formatPercent(pojo.getThreshold());
        this.avail = CommUtil.formatKB(usage.getAvail());
        this.files = usage.getFiles();
        this.freeFiles = usage.getFreeFiles();
        this.diskReads = usage.getDiskReads();
        this.diskWrites = usage.getDiskWrites();
        this.diskReadBytes = CommUtil.formatByte(usage.getDiskReadBytes());
        this.diskWriteBytes = CommUtil.formatByte(usage.getDiskWriteBytes());
        this.diskQueue = usage.getDiskQueue();
        this.diskServiceTime = usage.getDiskServiceTime();
        this.usePercent = CommUtil.formatPercent(usage.getUsePercent());
        this.total = CommUtil.formatKB(usage.getTotal());
        this.free = CommUtil.formatKB(usage.getFree());
        this.used = CommUtil.formatKB(usage.getUsed());
    }

    public String getDirName() {
        return dirName;
    }

    public String getThresholdPercent() {
        return thresholdPercent;
    }

    public String getAvail() {
        return avail;
    }

    public long getFiles() {
        return files;
    }

    public long getFreeFiles() {
        return freeFiles;
    }

    public long getDiskReads() {
        return diskReads;
    }

    public long getDiskWrites() {
        return diskWrites;
    }

    public String getDiskReadBytes() {
        return diskReadBytes;
    }

    public String getDiskWriteBytes() {
        return diskWriteBytes;
    }

    public double getDiskQueue() {
        return diskQueue;
    }

    public double getDiskServiceTime() {
        return diskServiceTime;
    }

    public String getUsePercent() {
        return usePercent;
    }

    public String getTotal() {
        return total;
    }

    public String getFree() {
        return free;
    }

    public String getUsed() {
        return used;
    }
}