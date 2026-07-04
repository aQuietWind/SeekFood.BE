package com.seek.food.config.NacosConfig.Common;


import com.seek.food.config.Data.JWTData;
import com.seek.food.config.Enum.ConfigKeyEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.HashMap;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.JWT_Config)
public class JWTConfig {
    private JWTData user;
    private JWTData merchant;
    private JWTData rider;
    private JWTData admin;
    private String headerSeparator;
    private String headerTokenName;
    private String RequestTokenName;
    private String maxStore;

    public JWTConfig() {
    }

    public JWTConfig(JWTData user, JWTData merchant, JWTData rider, JWTData admin, String headerSeparator, String headerTokenName, String RequestTokenName, String maxStore) {
        this.user = user;
        this.merchant = merchant;
        this.rider = rider;
        this.admin = admin;
        this.headerSeparator = headerSeparator;
        this.headerTokenName = headerTokenName;
        this.RequestTokenName = RequestTokenName;
        this.maxStore = maxStore;
    }

    public JWTData[] getAllJWTData() {
        return new JWTData[]{user, merchant, rider, admin};
    }

    /**
     * 获取
     * @return user
     */
    public JWTData getUser() {
        return user;
    }

    /**
     * 设置
     * @param user
     */
    public void setUser(JWTData user) {
        this.user = user;
    }

    /**
     * 获取
     * @return merchant
     */
    public JWTData getMerchant() {
        return merchant;
    }

    /**
     * 设置
     * @param merchant
     */
    public void setMerchant(JWTData merchant) {
        this.merchant = merchant;
    }

    /**
     * 获取
     * @return rider
     */
    public JWTData getRider() {
        return rider;
    }

    /**
     * 设置
     * @param rider
     */
    public void setRider(JWTData rider) {
        this.rider = rider;
    }

    /**
     * 获取
     * @return admin
     */
    public JWTData getAdmin() {
        return admin;
    }

    /**
     * 设置
     * @param admin
     */
    public void setAdmin(JWTData admin) {
        this.admin = admin;
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
     * @return RequestTokenName
     */
    public String getRequestTokenName() {
        return RequestTokenName;
    }

    /**
     * 设置
     * @param RequestTokenName
     */
    public void setRequestTokenName(String RequestTokenName) {
        this.RequestTokenName = RequestTokenName;
    }

    /**
     * 获取
     * @return maxStore
     */
    public String getMaxStore() {
        return maxStore;
    }

    /**
     * 设置
     * @param maxStore
     */
    public void setMaxStore(String maxStore) {
        this.maxStore = maxStore;
    }

    public String toString() {
        return "JWTConfig{user = " + user + ", merchant = " + merchant + ", rider = " + rider + ", admin = " + admin + ", headerSeparator = " + headerSeparator + ", headerTokenName = " + headerTokenName + ", RequestTokenName = " + RequestTokenName + ", maxStore = " + maxStore + "}";
    }
}
