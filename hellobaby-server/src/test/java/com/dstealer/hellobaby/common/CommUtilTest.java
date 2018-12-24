package com.dstealer.hellobaby.common;

import org.junit.Test;

/**
 * Created by LiShiwu on 05/04/2017.
 */
public class CommUtilTest {
    @Test
    public void avgExMaxAndMin() throws Exception {
        System.out.println(CommUtil.avgExMaxMin(new double[]{0D}));
        System.out.println(CommUtil.avgExMaxMin(new double[]{1.1,2.2}));
        System.out.println(CommUtil.avgExMaxMin(new double[]{2.0,3.0,4.0}));
        System.out.println(CommUtil.avgExMaxMin(new double[]{5.0,6.0,7.0,8.0}));
        System.out.println(CommUtil.avgExMaxMin(new double[]{5.0,5.0,8.0,8.0}));
    }
}