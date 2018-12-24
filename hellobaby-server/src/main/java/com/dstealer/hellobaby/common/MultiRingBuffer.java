package com.dstealer.hellobaby.common;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by LiShiwu on 05/28/2017.
 */
public class MultiRingBuffer<K, V> {
    /**
     * 新建RingBuffer默认大小
     */
    private int defaultInitialCapacity;
    /**
     * 内置容器
     */
    private ConcurrentHashMap<K, RingBuffer<V>> bufferMap;

    /**
     * 带默认大小的构造器
     *
     * @param defaultInitialCapacity
     */
    public MultiRingBuffer(int defaultInitialCapacity) {
        this.defaultInitialCapacity = defaultInitialCapacity;
        this.bufferMap = new ConcurrentHashMap<>();
    }

    /**
     * 添加指定RingBuffer
     *
     * @param k
     * @return
     */
    public RingBuffer<V> newRingBuffer(K k) {
        return this.bufferMap.computeIfAbsent(k, ik -> new RingBuffer<>(this.defaultInitialCapacity));
    }

    /**
     * 添加指定大小的RingBuffer
     * TODO 该不该开放
     *
     * @param k
     * @return
     */
    private RingBuffer<V> newRingBuffer(K k, int capacity) {
        return this.bufferMap.computeIfAbsent(k, ik -> new RingBuffer<>(capacity));
    }

    /**
     * 添加 如果不存在则抛出异常
     *
     * @param k
     * @param v
     */
    public void put(K k, V v) {
        RingBuffer<V> ringBuffer = this.bufferMap.get(k);
        if (ringBuffer != null) {
            ringBuffer.add(v);
        } else {
            throw new IllegalArgumentException("No buffer associate with :" + k);
        }
    }

    /**
     * 添加 如果不存在则新建
     *
     * @param k
     * @param v
     */
    public void putIfBufferAbsent(K k, V v) {
        this.bufferMap.computeIfAbsent(k, ik -> new RingBuffer<>(this.defaultInitialCapacity)).add(v);
    }

    /**
     * 清空 与之关联的RingBuffer
     *
     * @param k
     */
    public void clear(K k) {
        RingBuffer<V> ringBuffer = this.bufferMap.get(k);
        if (ringBuffer != null) {
            ringBuffer.clear();
        } else {
            throw new IllegalArgumentException("No buffer associate with :" + k);
        }
    }
    /**
     * 清空 与之关联的RingBuffer
     *
     * @param k
     */
    public void clearIfBufferPresent(K k) {
        RingBuffer<V> ringBuffer = this.bufferMap.get(k);
        if (ringBuffer != null) {
            ringBuffer.clear();
        } else {
            throw new IllegalArgumentException("No buffer associate with :" + k);
        }
    }

    /**
     * 移除与之关联的RingBuffer
     *
     * @param k
     */
    public void removeBuffer(K k) {
        this.bufferMap.remove(k);
    }

    /**
     * 移除所有RingBuffer
     */
    public void removeAllBuffer() {
        this.bufferMap.clear();
    }

    /**
     * 清空所有RingBuffer
     */
    public void clearAll() {
        this.bufferMap.forEach((k, vs) -> vs.clear());
    }

    /**
     * 与之关联的RingBuffer是否为空
     *
     * @param k
     * @return
     */
    public boolean isEmpty(K k) {
        RingBuffer<V> ringBuffer = this.bufferMap.get(k);
        if (ringBuffer != null) {
            return ringBuffer.isEmpty();
        } else {
            throw new IllegalArgumentException("No buffer associate with :" + k);
        }
    }

    /**
     * 与之关联的RingBuffer是否已满
     *
     * @param k
     * @return
     */
    public boolean isFull(K k) {
        RingBuffer<V> ringBuffer = this.bufferMap.get(k);
        if (ringBuffer != null) {
            return ringBuffer.isFull();
        } else {
            throw new IllegalArgumentException("No buffer associate with :" + k);
        }
    }

    /**
     * 与之关联的RingBuffer是否包含指定值
     *
     * @param k
     * @param v
     * @return
     */
    public boolean contains(K k, V v) {
        RingBuffer<V> ringBuffer = this.bufferMap.get(k);
        if (ringBuffer != null) {
            return ringBuffer.contain(v);
        } else {
            throw new IllegalArgumentException("No buffer associate with :" + k);
        }
    }

    /**
     * 与之关联的RingBuffer的大小
     * 返回
     *
     * @param k
     * @return
     */
    public int bufferSize(K k) {
        RingBuffer<V> ringBuffer = this.bufferMap.get(k);
        if (ringBuffer != null) {
            return ringBuffer.size();
        } else {
            throw new IllegalArgumentException("No buffer associate with :" + k);
        }
    }

    /**
     * 当前RingBuffer的数量
     *
     * @return
     */
    public int size() {
        return this.bufferMap.size();
    }

    /**
     * 以数组形式获取集合
     *
     * @param k
     * @return
     */
    public V[] getAsArray(K k) {
        RingBuffer<V> ringBuffer = this.bufferMap.get(k);
        if (ringBuffer != null) {
            return ringBuffer.toArray();
        } else {
            throw new IllegalArgumentException("No buffer associate with :" + k);
        }
    }

    /**
     * 以数组形式获取集合
     *
     * @param k
     * @return
     */
    public V[] getAsArray(K k, V[] vs) {
        RingBuffer<V> ringBuffer = this.bufferMap.get(k);
        if (ringBuffer != null) {
            return ringBuffer.toArray(vs);
        } else {
            throw new IllegalArgumentException("No buffer associate with :" + k);
        }
    }

    /**
     * 获取
     *
     * @param k
     * @return
     */
    public RingBuffer<V> getAsRingBuffer(K k) {
        RingBuffer<V> ringBuffer = this.bufferMap.get(k);
        if (ringBuffer != null) {
            return ringBuffer;
        } else {
            throw new IllegalArgumentException("No buffer associate with :" + k);
        }
    }

    /**
     * 获取 如不存在则新建
     *
     * @param k
     * @return
     */
    public RingBuffer<V> getAsRingBufferIfAbsent(K k) {
        return this.bufferMap.computeIfAbsent(k, ik -> new RingBuffer<>(this.defaultInitialCapacity));
    }
}
