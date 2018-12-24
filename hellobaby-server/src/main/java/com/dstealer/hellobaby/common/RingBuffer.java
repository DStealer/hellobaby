package com.dstealer.hellobaby.common;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 简化版RingBuffer实现
 * 线程安全
 * Created by LiShiwu on 05/03/2017.
 */
public class RingBuffer<T> implements Iterable<T> {
    /**
     * 容量
     */
    private final int capacity;
    /**
     * 同步读写锁
     */
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    /**
     * 内部存储结构
     */
    private Object[] elements;
    /**
     * 下一个写入位置指针
     */
    private int writePos;
    /**
     * 当前剩余空间
     */
    private int available;

    public RingBuffer(int size) {
        this.elements = new Object[size];
        this.capacity = size;
        this.available = size;
        this.writePos = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new It(this.toArray());
    }

    /**
     * 添加元素
     *
     * @param t
     */
    public void add(final T t) {
        if (t == null) {
            throw new InvalidParameterException("Element can't be null");
        }
        this.lock.writeLock().lock();
        try {
            this.available = this.available > 0 ? this.available - 1 : 0;
            this.elements[this.writePos++ % this.capacity] = t;
            this.writePos = this.writePos < this.capacity ? this.writePos : 0;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * 判断集合是否为空
     */
    public boolean isEmpty() {
        this.lock.readLock().lock();
        try {
            return this.available == this.capacity;
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * 判断集合是否已满
     */
    public boolean isFull() {
        this.lock.readLock().lock();
        try {
            return this.available == 0;
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * 获取元素集合
     *
     * @return
     */
    public T[] toArray() {
        this.lock.readLock().lock();
        try {
            Object[] ar = new Object[this.capacity - this.available];
            if (this.available == 0) {//说明数据已经形成环状,读取数据为writePos到capacity-1,再由0到writePos-1
                System.arraycopy(this.elements, this.writePos, ar, 0, this.capacity - this.writePos);
                System.arraycopy(this.elements, 0, ar, this.capacity - this.writePos, this.writePos);
            } else {//说明还未形成环状,数据顺序是从0开始,全部读取即可
                System.arraycopy(this.elements, 0, ar, 0, this.capacity - this.available);
            }
            return (T[]) ar;
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * 获取元素集合
     *
     * @return
     */
    public T[] toArray(T[] a) {
        if (a == null || a.length != 0) {
            throw new InvalidParameterException();
        }
        T[] ar = this.toArray();
        return (T[]) Arrays.copyOf(ar, ar.length, a.getClass());
    }

    /**
     * 当前集合大小
     *
     * @return
     */
    public int size() {
        this.lock.readLock().lock();
        try {
            return this.capacity - this.available;
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * 清空集合
     */
    public void clear() {
        this.lock.writeLock().lock();
        try {
            this.available = this.capacity;
            this.writePos = 0;
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    /**
     * 是否包含某个元素
     *
     * @param t
     * @return
     */
    public boolean contain(final T t) {
        if (t == null) {
            throw new InvalidParameterException("Element can't be null");
        }
        this.lock.readLock().lock();
        try {
            for (Object e : this.elements) {
                if (t.equals(e)) {
                    return true;
                }
            }
            return false;
        } finally {
            this.lock.readLock().unlock();
        }
    }

    /**
     * 遍历集合对象Iterator
     *
     * @param <T>
     */
    private static class It<T> implements Iterator {
        private T[] elements;
        private int readPos;

        public It(T[] elements) {
            this.elements = elements;
            this.readPos = 0;
        }

        @Override
        public boolean hasNext() {
            return this.readPos < elements.length;
        }

        @Override
        public Object next() {
            return this.elements[this.readPos++];
        }
    }
}
