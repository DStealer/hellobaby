package com.dstealer.hellobaby.client.common;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 非严谨的单例管理
 * Created by LiShiwu on 05/28/2017.
 */
public class SingletonManager {
    private static final ConcurrentHashMap<Class, Object> MAP = new ConcurrentHashMap<>();

    /**
     * 获取单例，非严谨
     *
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T extends SingletonFactory<T>> T get(Class<T> clazz) {
        return (T) MAP.computeIfAbsent(clazz, aClass -> {
            try {
                return clazz.newInstance().create();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
