package com.seek.food.gateway.Enum;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
public class JWTConfig {
    @Value("${jwt.key.user}")
    private String userSerectKey;
    @Value("${jwt.key.merchant}")
    private String merchantSerectKey;


    public JWTConfig() {
    }

    public JWTConfig(String userSerectKey, String merchantSerectKey) {
        this.userSerectKey = userSerectKey;
        this.merchantSerectKey = merchantSerectKey;
    }

    /**
     * 获取
     * @return userSerectKey
     */
    public String getUserSerectKey() {
        return userSerectKey;
    }

    /**
     * 设置
     * @param userSerectKey
     */
    public void setUserSerectKey(String userSerectKey) {
        this.userSerectKey = userSerectKey;
    }

    /**
     * 获取
     * @return merchantSerectKey
     */
    public String getMerchantSerectKey() {
        return merchantSerectKey;
    }

    /**
     * 设置
     * @param merchantSerectKey
     */
    public void setMerchantSerectKey(String merchantSerectKey) {
        this.merchantSerectKey = merchantSerectKey;
    }

    public String toString() {
        return "JWTEnum{userSerectKey = " + userSerectKey + ", merchantSerectKey = " + merchantSerectKey + "}";
    }
}
