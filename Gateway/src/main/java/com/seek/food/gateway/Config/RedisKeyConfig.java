package com.seek.food.gateway.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class RedisKeyConfig {
    @Value("${redis.key.name.token-login}")
    private String loginTokenKey;
    @Value("${redis.key.name.id-check}")
    private String idCheckKey;
    @Value("${redis.key.name.ip-check}")
    private String ipCheckKey;
    @Value("${redis.key.name.id-black}")
    private String idBlackKey;
    @Value("${redis.key.name.ip-black}")
    private String ipBlackKey;


    public RedisKeyConfig() {
    }

    public RedisKeyConfig(String loginTokenKey, String idCheckKey, String ipCheckKey, String idBlackKey, String ipBlackKey) {
        this.loginTokenKey = loginTokenKey;
        this.idCheckKey = idCheckKey;
        this.ipCheckKey = ipCheckKey;
        this.idBlackKey = idBlackKey;
        this.ipBlackKey = ipBlackKey;
    }


    /**
     * 获取
     * @return loginTokenKey
     */
    public String getLoginTokenKey() {
        return loginTokenKey;
    }

    /**
     * 设置
     * @param loginTokenKey
     */
    public void setLoginTokenKey(String loginTokenKey) {
        this.loginTokenKey = loginTokenKey;
    }

    /**
     * 获取
     * @return ipCheckKey
     */
    public String getIpCheckKey() {
        return ipCheckKey;
    }

    /**
     * 设置
     * @param ipCheckKey
     */
    public void setIpCheckKey(String ipCheckKey) {
        this.ipCheckKey = ipCheckKey;
    }

    /**
     * 获取
     * @return idCheckKey
     */
    public String getIdCheckKey() {
        return idCheckKey;
    }

    /**
     * 设置
     * @param idCheckKey
     */
    public void setIdCheckKey(String idCheckKey) {
        this.idCheckKey = idCheckKey;
    }


    /**
     * 获取
     * @return idBlackKey
     */
    public String getIdBlackKey() {
        return idBlackKey;
    }

    /**
     * 设置
     * @param idBlackKey
     */
    public void setIdBlackKey(String idBlackKey) {
        this.idBlackKey = idBlackKey;
    }

    /**
     * 获取
     * @return ipBlackKey
     */
    public String getIpBlackKey() {
        return ipBlackKey;
    }

    /**
     * 设置
     * @param ipBlackKey
     */
    public void setIpBlackKey(String ipBlackKey) {
        this.ipBlackKey = ipBlackKey;
    }

    public String toString() {
        return "RedisKeyConfig{loginTokenKey = " + loginTokenKey + ", idCheckKey = " + idCheckKey + ", ipCheckKey = " + ipCheckKey + ", idBlackKey = " + idBlackKey + ", ipBlackKey = " + ipBlackKey + "}";
    }
}
