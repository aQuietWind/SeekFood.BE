package com.seek.food.config.NacosConfig.Common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@ConfigurationProperties("common.redis.key.name")
public class CommonRedisKeyConfig {
    private String loginToken;
    /**
     * 获取
     * @return loginToken
     */
    public String getLoginToken() {
        return loginToken;
    }

}
