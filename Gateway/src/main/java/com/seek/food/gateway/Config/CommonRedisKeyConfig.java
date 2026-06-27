package com.seek.food.gateway.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@ConfigurationProperties("common.redis.key.name")
public class CommonRedisKeyConfig {
    private String loginToken;

    public CommonRedisKeyConfig() {
    }

    public CommonRedisKeyConfig(String loginToken) {
        this.loginToken = loginToken;
    }

    /**
     * 获取
     * @return loginToken
     */
    public String getLoginToken() {
        return loginToken;
    }

    /**
     * 设置
     * @param loginToken
     */
    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String toString() {
        return "CommonRedisKeyConfig{loginToken = " + loginToken + "}";
    }
}
