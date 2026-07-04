package com.seek.food.config.NacosConfig.User;

import com.seek.food.config.Enum.ConfigKeyEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.User_Caffeine_Config)
public class UserCaffeineConfig {
    private int maxSize;
    private long expireTime;

    public UserCaffeineConfig() {
    }

    public UserCaffeineConfig(int maxSize, long expireTime) {
        this.maxSize = maxSize;
        this.expireTime = expireTime;
    }

    /**
     * 获取
     * @return maxSize
     */
    public int getMaxSize() {
        return maxSize;
    }

    /**
     * 设置
     * @param maxSize
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * 获取
     * @return expireTime
     */
    public long getExpireTime() {
        return expireTime;
    }

    /**
     * 设置
     * @param expireTime
     */
    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public String toString() {
        return "UserCaffeineConfig{maxSize = " + maxSize + ", expireTime = " + expireTime + "}";
    }
}
