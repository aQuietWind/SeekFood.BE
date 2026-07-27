package com.seek.food.config.NacosConfig.Admin;

import com.seek.food.config.Data.RedisKeyData;
import com.seek.food.config.Enum.ConfigKeyEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Data
@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.Admin_Redis_Key_Config)
public class AdminRedisKeyConfig {
    private RedisKeyData mealInsertCooldown;
    private RedisKeyData mealGetSimpleCooldown;
    private RedisKeyData mealGetSimpleByTypeCooldown;
    private RedisKeyData mealUpdateMessageCooldown;
    private RedisKeyData mealUpdatePriceCooldown;
    private RedisKeyData mealUpdateShowImageCooldown;
    private RedisKeyData mealUpdateSellCooldown;
    private RedisKeyData mealDeleteCooldown;
    private RedisKeyData mealIdCount;
    private RedisKeyData mealMessageCaffeine;
}
