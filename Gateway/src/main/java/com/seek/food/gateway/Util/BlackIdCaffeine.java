package com.seek.food.gateway.Util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.seek.food.gateway.Config.GatewayConfig;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
@Component
public class BlackIdCaffeine {
    // 全局单例缓存（唯一实例）
    private Cache<String, Long> CACHE;

    // 构造注入配置
    private final GatewayConfig gatewayConfig;
    @Autowired
    public BlackIdCaffeine(GatewayConfig gatewayConfig) {
        this.gatewayConfig = gatewayConfig;
    }

    // 容器启动构建缓存
    @PostConstruct
    public void init() {
        this.CACHE = Caffeine.newBuilder()
                .maximumSize(gatewayConfig.getCaffeineBlackIdSize())
                .expireAfterWrite(gatewayConfig.getCaffeineBlackIdExpire(), TimeUnit.MINUTES)
                .recordStats()
                .build();
    }

    // 容器销毁清理缓存
    @PreDestroy
    public void destroy() {
        this.CACHE.cleanUp();
        this.CACHE.invalidateAll();
    }
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
