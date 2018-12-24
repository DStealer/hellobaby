package com.dstealer.hellobaby.client.common;

/**
 * 单例建造工厂
 * Created by LiShiwu on 05/28/2017.
 */
@FunctionalInterface
public interface SingletonFactory<T> {
    T create() throws Exception;
}
