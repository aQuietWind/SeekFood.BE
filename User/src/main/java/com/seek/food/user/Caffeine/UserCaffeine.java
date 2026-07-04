package com.seek.food.user.Caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.seek.food.config.NacosConfig.User.UserCaffeineConfig;
import com.seek.food.util.Caffeine.JvmCaffeineParent;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class UserCaffeine extends JvmCaffeineParent {
    // 全局单例缓存（唯一实例）
    private Cache<Long, String> CACHE;

    // 构造注入配置
    private final UserCaffeineConfig userCaffeineConfig;
    @Autowired
    public UserCaffeine(UserCaffeineConfig userCaffeineConfig) {
        this.userCaffeineConfig = userCaffeineConfig;
    }

    // 容器启动构建缓存
    @PostConstruct
    public void init() {
        this.CACHE = Caffeine.newBuilder()
                .maximumSize(userCaffeineConfig.getMaxSize())
                .expireAfterWrite(userCaffeineConfig.getExpireTime(), TimeUnit.MINUTES)
                .recordStats()
                .build();
    }

    // 容器销毁清理缓存
    @PreDestroy
    public void destroy() {
        this.CACHE.cleanUp();
        this.CACHE.invalidateAll();
    }
}
