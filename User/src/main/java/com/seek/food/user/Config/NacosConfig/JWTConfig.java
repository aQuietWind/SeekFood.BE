package com.seek.food.user.Config.NacosConfig;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@Component
public class JWTConfig {
    @Value("${common.jwt.secret-key.user}")
    private String userSecretKey;
    @Value("${common.jwt.header-sign.user}")
    private String userHeaderSign;
    @Value("${common.jwt.header-separator}")
    private String headerSeparator;
    @Value("${common.jwt.header-token-name}")
    private String headerTokenName;
    @Value("${common.jwt.request-token-name}")
    private String requestTokenName;
    @Value("${common.jwt.token-duration}")
    private long tokenDuration;

    public JWTConfig() {
    }

    public JWTConfig(String userSecretKey, String userHeaderSign, String headerSeparator, String headerTokenName, String requestTokenName, long tokenDuration) {
        this.userSecretKey = userSecretKey;
        this.userHeaderSign = userHeaderSign;
        this.headerSeparator = headerSeparator;
        this.headerTokenName = headerTokenName;
        this.requestTokenName = requestTokenName;
        this.tokenDuration = tokenDuration;
    }

    /**
     * 获取
     * @return userSecretKey
     */
    public String getUserSecretKey() {
        return userSecretKey;
    }

    /**
     * 设置
     * @param userSecretKey
     */
    public void setUserSecretKey(String userSecretKey) {
        this.userSecretKey = userSecretKey;
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

    /**
     * 获取
     * @return requestTokenName
     */
    public String getRequestTokenName() {
        return requestTokenName;
    }

    /**
     * 设置
     * @param requestTokenName
     */
    public void setRequestTokenName(String requestTokenName) {
        this.requestTokenName = requestTokenName;
    }

    /**
     * 获取
     * @return tokenDuration
     */
    public long getTokenDuration() {
        return tokenDuration;
    }

    /**
     * 设置
     * @param tokenDuration
     */
    public void setTokenDuration(long tokenDuration) {
        this.tokenDuration = tokenDuration;
    }

    public String toString() {
        return "JWTConfig{userSecretKey = " + userSecretKey + ", userHeaderSign = " + userHeaderSign + ", headerSeparator = " + headerSeparator + ", headerTokenName = " + headerTokenName + ", requestTokenName = " + requestTokenName + ", tokenDuration = " + tokenDuration + "}";
    }
}
