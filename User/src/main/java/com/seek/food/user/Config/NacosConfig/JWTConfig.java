package com.seek.food.user.Config.NacosConfig;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
public class JWTConfig {
    @Value("${common.jwt.secret-key.user}")
    private String userSerectKey;
    @Value("${common.jwt.header-sign.user}")
    private String userHeaderSign;
    @Value("${common.jwt.header-separator}")
    private String headerSeparator;
    @Value("${common.jwt.header-token-name}")
    private String headerTokenName;

    public JWTConfig() {
    }

    public JWTConfig(String userSerectKey, String userHeaderSign, String headerSeparator, String headerTokenName) {
        this.userSerectKey = userSerectKey;
        this.userHeaderSign = userHeaderSign;
        this.headerSeparator = headerSeparator;
        this.headerTokenName = headerTokenName;
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
     * @return userHeaderSign
     */
    public String getUserHeaderSign() {
        return userHeaderSign;
    }

    /**
     * 设置
     * @param userHeaderSign
     */
    public void setUserHeaderSign(String userHeaderSign) {
        this.userHeaderSign = userHeaderSign;
    }

    /**
     * 获取
     * @return headerSeparator
     */
    public String getHeaderSeparator() {
        return headerSeparator;
    }

    /**
     * 设置
     * @param headerSeparator
     */
    public void setHeaderSeparator(String headerSeparator) {
        this.headerSeparator = headerSeparator;
    }

    /**
     * 获取
     * @return headerTokenName
     */
    public String getHeaderTokenName() {
        return headerTokenName;
    }

    /**
     * 设置
     * @param headerTokenName
     */
    public void setHeaderTokenName(String headerTokenName) {
        this.headerTokenName = headerTokenName;
    }

    public String toString() {
        return "JWTConfig{userSerectKey = " + userSerectKey + ", userHeaderSign = " + userHeaderSign + ", headerSeparator = " + headerSeparator + ", headerTokenName = " + headerTokenName + "}";
    }
}
