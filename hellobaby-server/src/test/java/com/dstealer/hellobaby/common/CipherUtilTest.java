package com.dstealer.hellobaby.common;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by LiShiwu on 02/18/2017.
 */
public class CipherUtilTest {
    @Test
    public void test001() throws Exception {
        String plainText = "这是一个测试而已";
        String cipherText = CipherUtil.encrypt(plainText);
        System.out.printf("plainText:[%1$s]\n", plainText);
        System.out.printf("cipherText:[%1$s]\n", cipherText);
        Assert.assertEquals(CipherUtil.decrypt(cipherText), plainText);
    }

    @Test
    public void test002() throws Exception {
        CipherUtil.main(new String[]{"test"});
    }
}