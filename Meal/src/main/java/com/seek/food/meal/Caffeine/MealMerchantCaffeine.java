package com.seek.food.meal.Caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.seek.food.config.NacosConfig.Meal.MealCaffeineConfig;
import com.seek.food.dto.Meal.MealDTO;
import com.seek.food.util.Caffeine.JvmCaffeineParent;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

//专门用于缓存商家端获取的餐品数据
@Component
public class MealMerchantCaffeine extends JvmCaffeineParent<Long, MealDTO> {
    // 构造注入配置
    private final MealCaffeineConfig mealCaffeineConfig;
    @Autowired
    public MealMerchantCaffeine(MealCaffeineConfig mealCaffeineConfig) {
        this.mealCaffeineConfig = mealCaffeineConfig;
    }

    // 容器启动构建缓存
    @PostConstruct
    public void init() {
        super.CACHE = Caffeine.newBuilder()
                .maximumSize(mealCaffeineConfig.getMealMerchant().getMaxSize())
                .expireAfterWrite(mealCaffeineConfig.getMealMerchant().getExpireTime(), TimeUnit.MINUTES)
                .recordStats()
                .build();
    }

    // 容器销毁清理缓存
    @PreDestroy
    public void destroy() {
        super.CACHE.cleanUp();
        super.CACHE.invalidateAll();
    }
}
