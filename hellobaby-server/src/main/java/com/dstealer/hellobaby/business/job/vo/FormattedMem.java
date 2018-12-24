package com.dstealer.hellobaby.business.job.vo;

import com.dstealer.hellobaby.common.CommUtil;
import com.dstealer.hellobaby.common.DateUtil;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Swap;

import java.util.Date;

/**
 * Created by LiShiwu on 05/04/2017.
 */
public class FormattedMem {
    private String gatheringTime;
    private String mtotal;
    private String mram;
    private String mused;
    private String mfree;
    private String mactualUsed;
    private String mactualFree;
    private String musedPercent;
    private String mfreePercent;
    private String stotal;
    private String sused;
    private String sfree;
    private String spageIn;
    private String spageOut;
    private double originalActualUsedPer;

    public FormattedMem(Date date, Mem mem, Swap swap) {
        this.gatheringTime = DateUtil.format(date, "yyyy-MM-dd HH:mm:ss");
        this.mtotal = CommUtil.formatByte(mem.getTotal());
        this.mram = CommUtil.formatByte(mem.getRam());
        this.mused = CommUtil.formatByte(mem.getUsed());
        this.mfree = CommUtil.formatByte(mem.getFree());
        this.mactualUsed = CommUtil.formatByte(mem.getActualUsed());
        this.mactualFree = CommUtil.formatByte(mem.getActualFree());
        this.musedPercent = CommUtil.formatPercent(mem.getUsedPercent() / 100D);
        this.mfreePercent = CommUtil.formatPercent(mem.getFreePercent() / 100D);
        this.stotal = CommUtil.formatByte(swap.getUsed());
        this.sused = CommUtil.formatByte(swap.getUsed());
        this.sfree = CommUtil.formatByte(swap.getFree());
        this.spageIn = CommUtil.formatByte(swap.getPageIn());
        this.spageOut = CommUtil.formatByte(swap.getPageOut());
        this.originalActualUsedPer = mem.getUsedPercent() / 100D;
    }

    public String getGatheringTime() {
        return gatheringTime;
    }

    public String getMtotal() {
        return mtotal;
    }

    public String getMram() {
        return mram;
    }

    public String getMused() {
        return mused;
    }

    public String getMfree() {
        return mfree;
    }

    public String getMactualUsed() {
        return mactualUsed;
    }

    public String getMactualFree() {
        return mactualFree;
    }

    public String getMusedPercent() {
        return musedPercent;
    }

    public String getMfreePercent() {
        return mfreePercent;
    }

    public String getStotal() {
        return stotal;
    }

    public String getSused() {
        return sused;
    }

    public String getSfree() {
        return sfree;
    }

    public String getSpageIn() {
        return spageIn;
    }

    public String getSpageOut() {
        return spageOut;
    }

    public double getOriginalActualUsedPer() {
        return originalActualUsedPer;
    }
}
