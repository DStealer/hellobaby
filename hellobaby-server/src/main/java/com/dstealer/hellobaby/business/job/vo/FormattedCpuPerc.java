package com.dstealer.hellobaby.business.job.vo;

import com.dstealer.hellobaby.common.DateUtil;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.SigarLoader;

import java.util.Date;

/**
 * Created by LiShiwu on 05/03/2017.
 */
public class FormattedCpuPerc {
    private String user;
    private String sys;
    private String nice;
    private String idle;
    private String wait;
    private String irq;
    private String softIrq;
    private String stolen;
    private String combined;
    private String gatheringTime;
    private double originalCombined;

    public FormattedCpuPerc(Date date, CpuPerc cpuPerc) {
        this.user = CpuPerc.format(cpuPerc.getUser());
        this.sys = CpuPerc.format(cpuPerc.getSys());
        this.idle = CpuPerc.format(cpuPerc.getIdle());
        this.wait = CpuPerc.format(cpuPerc.getWait());
        this.nice = CpuPerc.format(cpuPerc.getNice());
        this.combined = CpuPerc.format(cpuPerc.getCombined());
        this.irq = CpuPerc.format(cpuPerc.getIrq());
        if (SigarLoader.IS_LINUX) {
            this.softIrq = CpuPerc.format(cpuPerc.getSoftIrq());
            this.stolen = CpuPerc.format(cpuPerc.getStolen());
        }
        this.gatheringTime = DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
        this.originalCombined = cpuPerc.getCombined();
    }

    public String getUser() {
        return user;
    }

    public String getSys() {
        return sys;
    }

    public String getNice() {
        return nice;
    }

    public String getIdle() {
        return idle;
    }

    public String getWait() {
        return wait;
    }

    public String getIrq() {
        return irq;
    }

    public String getSoftIrq() {
        return softIrq;
    }

    public String getStolen() {
        return stolen;
    }

    public String getCombined() {
        return combined;
    }

    public String getGatheringTime() {
        return gatheringTime;
    }

    public double getOriginalCombined() {
        return originalCombined;
    }
}
