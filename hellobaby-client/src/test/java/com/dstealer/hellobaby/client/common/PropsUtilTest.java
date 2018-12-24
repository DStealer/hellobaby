package com.dstealer.hellobaby.client.common;

import org.junit.Test;

import java.util.Arrays;

/**
 * Created by LiShiwu on 05/26/2017.
 */
public class PropsUtilTest {
    @Test
    public void tt1() throws Exception {
        PropsUtil util = PropsUtil.loadFromPath("D:\\Workspace\\serverprojects_other\\hellobabyclient\\src\\main\\resources\\app.properties");
        util.list(System.out);
    }

    @Test
    public void tt2() throws Exception {
        PropsUtil util = PropsUtil.loadFromClassPath("app.properties");
        System.out.println(util.get("key1"));
        System.out.println(Arrays.toString(util.getStringArray("key3")));
        util.list(System.out);
    }
}