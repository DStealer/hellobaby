package com.dstealer.hellobaby.common;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;

import java.util.Arrays;

/**
 * 通用工具类，如果不知道写到哪里合适，可以写到这里
 * Created by LiShiwu on 02/18/2017.
 */
public class CommUtil {
    /**
     * 格式化byte显示
     *
     * @param l
     * @return
     */
    public static String formatByte(long l) {
        return Sigar.formatSize(l);
    }

    /**
     * 格式化byte显示
     *
     * @param l
     * @return
     */
    public static String formatKB(long l) {
        return Sigar.formatSize(l * 1024L);
    }

    /**
     * 将小数类型格式化为百分号形式
     *
     * @param p
     * @return
     */
    public static String formatPercent(double p) {
        return CpuPerc.format(p);
    }

    /**
     * 计算平均值,排除一个最大值和一个最小值
     *
     * @return
     */
    public static double avgExMaxMin(double[] params) {
        if (params == null || params.length <= 2) {
            return 0D;
        } else {
            Arrays.sort(params);
            double sum = 0D;
            for (int i = 1; i < params.length - 1; i++) {
                sum += params[i];
            }
            return sum / (params.length - 2);
        }
    }
}
