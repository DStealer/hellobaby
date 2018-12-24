package com.dstealer.hellobaby.common;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by LiShiwu on 05/03/2017.
 */
public class RingBufferTest {
    @Test
    public void tt1() throws Exception {
        RingBuffer<Integer> ringBuffer = new RingBuffer<>(5);
        Assert.assertTrue(ringBuffer.isEmpty());
        ringBuffer.add(1);
        ringBuffer.add(2);
        print(ringBuffer);
        Assert.assertTrue(ringBuffer.contain(1));
        Assert.assertFalse(ringBuffer.contain(6));

        ringBuffer.add(3);
        ringBuffer.add(4);
        ringBuffer.add(5);
        Assert.assertEquals(ringBuffer.size(), 5);
        Assert.assertTrue(!ringBuffer.isEmpty());
        Assert.assertTrue(ringBuffer.isFull());
        print(ringBuffer);

        ringBuffer.add(6);
        Assert.assertTrue(ringBuffer.contain(6));
        ringBuffer.add(7);
        Assert.assertEquals(ringBuffer.size(), 5);
        Assert.assertTrue(ringBuffer.isFull());

        print(ringBuffer);
        ringBuffer.add(8);
        ringBuffer.add(9);
        print(ringBuffer);
        ringBuffer.add(10);
        print(ringBuffer);
    }

    private void print(RingBuffer ringBuffer) {
        System.out.println("*************************");
        for (Object o : ringBuffer) {
            System.out.println(o);
        }
    }
}