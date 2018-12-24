package com.dstealer.hellobaby.common;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by LiShiwu on 05/29/2017.
 */
public class MultiRingBufferTest {
    @Test
    public void tt1() throws Exception {
        MultiRingBuffer<String, String> buffer = new MultiRingBuffer<>(0);
        buffer.newRingBuffer("key1");
        Assert.assertTrue(buffer.isEmpty("key1"));
        Assert.assertTrue(buffer.isFull("key1"));
    }

    @Test
    public void tt2() throws Exception {
        MultiRingBuffer<String, String> buffer = new MultiRingBuffer<>(2);
        buffer.putIfBufferAbsent("key1", "value1");
        buffer.putIfBufferAbsent("key1", "value2");
        Arrays.stream(buffer.getAsArray("key1")).forEach(System.out::println);
        buffer.putIfBufferAbsent("key1", "value3");
        buffer.putIfBufferAbsent("key1", "value4");
        Arrays.stream(buffer.getAsArray("key1")).forEach(System.out::println);
    }
    @Test
    public void tt3() throws Exception {
        MultiRingBuffer<String, String> buffer = new MultiRingBuffer<>(2);
        buffer.getAsArray("key1");
    }
}