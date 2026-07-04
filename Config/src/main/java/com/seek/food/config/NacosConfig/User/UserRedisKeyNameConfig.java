package com.seek.food.config.NacosConfig.User;

import com.seek.food.config.Enum.ConfigKeyEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@ConfigurationProperties(ConfigKeyEnum.User_Redis_Key_Name_Config)
public class UserRedisKeyNameConfig {
    private String registerOpt;
    private String loginOpt;
    private String deleteUserOpt;
    private String registerCooldown;
    private String updatePasswordOpt;
    private String updateHeaderImageCooldown;
    private String updateMessageCooldown;
    private String updatePasswordCooldown;
    private String caffeineMessage;
    private String userIdCount;
    private String loginPasswordCooldown;

    public UserRedisKeyNameConfig() {
    }

    public UserRedisKeyNameConfig(String registerOpt, String loginOpt, String deleteUserOpt, String registerCooldown, String updatePasswordOpt, String updateHeaderImageCooldown, String updateMessageCooldown, String updatePasswordCooldown, String caffeineMessage, String userIdCount, String loginPasswordCooldown) {
        this.registerOpt = registerOpt;
        this.loginOpt = loginOpt;
        this.deleteUserOpt = deleteUserOpt;
        this.registerCooldown = registerCooldown;
        this.updatePasswordOpt = updatePasswordOpt;
        this.updateHeaderImageCooldown = updateHeaderImageCooldown;
        this.updateMessageCooldown = updateMessageCooldown;
        this.updatePasswordCooldown = updatePasswordCooldown;
        this.caffeineMessage = caffeineMessage;
        this.userIdCount = userIdCount;
        this.loginPasswordCooldown = loginPasswordCooldown;
    }

    /**
     * 获取
     * @return registerOpt
     */
    public String getRegisterOpt() {
        return registerOpt;
    }

    /**
     * 设置
     * @param registerOpt
     */
    public void setRegisterOpt(String registerOpt) {
        this.registerOpt = registerOpt;
    }

    /**
     * 获取
     * @return loginOpt
     */
    public String getLoginOpt() {
        return loginOpt;
    }

    /**
     * 设置
     * @param loginOpt
     */
    public void setLoginOpt(String loginOpt) {
        this.loginOpt = loginOpt;
    }

    /**
     * 获取
     * @return deleteUserOpt
     */
    public String getDeleteUserOpt() {
        return deleteUserOpt;
    }

    /**
     * 设置
     * @param deleteUserOpt
     */
    public void setDeleteUserOpt(String deleteUserOpt) {
        this.deleteUserOpt = deleteUserOpt;
    }

    /**
     * 获取
     * @return registerCooldown
     */
    public String getRegisterCooldown() {
        return registerCooldown;
    }

    /**
     * 设置
     * @param registerCooldown
     */
    public void setRegisterCooldown(String registerCooldown) {
        this.registerCooldown = registerCooldown;
    }

    /**
     * 获取
     * @return updatePasswordOpt
     */
    public String getUpdatePasswordOpt() {
        return updatePasswordOpt;
    }

    /**
     * 设置
     * @param updatePasswordOpt
     */
    public void setUpdatePasswordOpt(String updatePasswordOpt) {
        this.updatePasswordOpt = updatePasswordOpt;
    }

    /**
     * 获取
     * @return updateHeaderImageCooldown
     */
    public String getUpdateHeaderImageCooldown() {
        return updateHeaderImageCooldown;
    }

    /**
     * 设置
     * @param updateHeaderImageCooldown
     */
    public void setUpdateHeaderImageCooldown(String updateHeaderImageCooldown) {
        this.updateHeaderImageCooldown = updateHeaderImageCooldown;
    }

    /**
     * 获取
     * @return updateMessageCooldown
     */
    public String getUpdateMessageCooldown() {
        return updateMessageCooldown;
    }

    /**
     * 设置
     * @param updateMessageCooldown
     */
    public void setUpdateMessageCooldown(String updateMessageCooldown) {
        this.updateMessageCooldown = updateMessageCooldown;
    }

    /**
     * 获取
     * @return updatePasswordCooldown
     */
    public String getUpdatePasswordCooldown() {
        return updatePasswordCooldown;
    }

    /**
     * 设置
     * @param updatePasswordCooldown
     */
    public void setUpdatePasswordCooldown(String updatePasswordCooldown) {
        this.updatePasswordCooldown = updatePasswordCooldown;
    }

    /**
     * 获取
     * @return caffeineMessage
     */
    public String getCaffeineMessage() {
        return caffeineMessage;
    }

    /**
     * 设置
     * @param caffeineMessage
     */
    public void setCaffeineMessage(String caffeineMessage) {
        this.caffeineMessage = caffeineMessage;
    }

    /**
     * 获取
     * @return userIdCount
     */
    public String getUserIdCount() {
        return userIdCount;
    }

    /**
     * 设置
     * @param userIdCount
     */
    public void setUserIdCount(String userIdCount) {
        this.userIdCount = userIdCount;
    }

    /**
     * 获取
     * @return loginPasswordCooldown
     */
    public String getLoginPasswordCooldown() {
        return loginPasswordCooldown;
    }

    /**
     * 设置
     * @param loginPasswordCooldown
     */
    public void setLoginPasswordCooldown(String loginPasswordCooldown) {
        this.loginPasswordCooldown = loginPasswordCooldown;
    }

    public String toString() {
        return "UserRedisKeyNameConfig{registerOpt = " + registerOpt + ", loginOpt = " + loginOpt + ", deleteUserOpt = " + deleteUserOpt + ", registerCooldown = " + registerCooldown + ", updatePasswordOpt = " + updatePasswordOpt + ", updateHeaderImageCooldown = " + updateHeaderImageCooldown + ", updateMessageCooldown = " + updateMessageCooldown + ", updatePasswordCooldown = " + updatePasswordCooldown + ", caffeineMessage = " + caffeineMessage + ", userIdCount = " + userIdCount + ", loginPasswordCooldown = " + loginPasswordCooldown + "}";
    }
}
