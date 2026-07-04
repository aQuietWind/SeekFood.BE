package com.seek.food.util.Caffeine;

import com.github.benmanes.caffeine.cache.Cache;

public class JvmCaffeineParent {
    // 全局单例缓存（唯一实例）
    private Cache<String, Long> CACHE;

    // ====================== 对外方法 ======================
    // 存缓存
    public void put(String key, Long value) {
        CACHE.put(key, value);
    }
    //取缓存，没有返回 null
    public Long get(String key) {
        return CACHE.getIfPresent(key);
    }
    // 取缓存，如果没有，自动执行 load 逻辑并写入缓存（最常用）
    public Long get(String key, java.util.function.Function<String, Long> loader) {
        return CACHE.get(key, loader);
    }
    //删除缓存
    public void delete(String key) {
        CACHE.invalidate(key);
    }
    //清空所有缓存
    public void clear() {
        CACHE.invalidateAll();
    }
    public Cache<String, Long> getCACHE() {
        return CACHE;
    }
}
