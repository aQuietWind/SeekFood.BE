package com.seek.food.gateway.Config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@RefreshScope
@Component
@ConfigurationProperties("common.jwt")
public class JWTConfig {
    private ArrayList<String> tokenName;
    private Map<String,String> secretKey;
    private Map<String,String> headerSign;
    private String headerSeparator;
    private String headerTokenName;
    private String requestTokenName;

    public JWTConfig() {
    }

    public JWTConfig(ArrayList<String> tokenName, Map<String, String> secretKey, Map<String, String> headerSign, String headerSeparator, String headerTokenName, String requestTokenName) {
        this.tokenName = tokenName;
        this.secretKey = secretKey;
        this.headerSign = headerSign;
        this.headerSeparator = headerSeparator;
        this.headerTokenName = headerTokenName;
        this.requestTokenName = requestTokenName;
    }

    /**
     * 获取
     * @return tokenName
     */
    public ArrayList<String> getTokenName() {
        return tokenName;
    }

    /**
     * 设置
     * @param tokenName
     */
    public void setTokenName(ArrayList<String> tokenName) {
        this.tokenName = tokenName;
    }

    /**
     * 获取
     * @return secretKey
     */
    public Map<String, String> getSecretKey() {
        return secretKey;
    }

    /**
     * 设置
     * @param secretKey
     */
    public void setSecretKey(Map<String, String> secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * 获取
     * @return headerSign
     */
    public Map<String, String> getHeaderSign() {
        return headerSign;
    }

    /**
     * 设置
     * @param headerSign
     */
    public void setHeaderSign(Map<String, String> headerSign) {
        this.headerSign = headerSign;
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

    public String toString() {
        return "JWTConfig{tokenName = " + tokenName + ", secretKey = " + secretKey + ", headerSign = " + headerSign + ", headerSeparator = " + headerSeparator + ", headerTokenName = " + headerTokenName + ", requestTokenName = " + requestTokenName + "}";
    }
}
