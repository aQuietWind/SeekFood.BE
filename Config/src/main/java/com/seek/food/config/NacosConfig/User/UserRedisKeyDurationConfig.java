package com.seek.food.config.NacosConfig.User;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@RefreshScope
@ConfigurationProperties("user.self.redis.key.duration")
public class UserRedisKeyDurationConfig {
    private long registerCooldown;
    private long opt;
    private long updateHeaderImageCooldown;
    private long updateMessageCooldown;
    private long updatePasswordCooldown;
    private long caffeineMessage;
    private long loginPasswordCooldown;

    public UserRedisKeyDurationConfig() {
    }

    public UserRedisKeyDurationConfig(long registerCooldown, long opt, long updateHeaderImageCooldown, long updateMessageCooldown, long updatePasswordCooldown, long caffeineMessage, long loginPasswordCooldown) {
        this.registerCooldown = registerCooldown;
        this.opt = opt;
        this.updateHeaderImageCooldown = updateHeaderImageCooldown;
        this.updateMessageCooldown = updateMessageCooldown;
        this.updatePasswordCooldown = updatePasswordCooldown;
        this.caffeineMessage = caffeineMessage;
        this.loginPasswordCooldown = loginPasswordCooldown;
    }

    /**
     * 获取
     * @return registerCooldown
     */
    public long getRegisterCooldown() {
        return registerCooldown;
    }

    /**
     * 设置
     * @param registerCooldown
     */
    public void setRegisterCooldown(long registerCooldown) {
        this.registerCooldown = registerCooldown;
    }

    /**
     * 获取
     * @return opt
     */
    public long getOpt() {
        return opt;
    }

    /**
     * 设置
     * @param opt
     */
    public void setOpt(long opt) {
        this.opt = opt;
    }

    /**
     * 获取
     * @return updateHeaderImageCooldown
     */
    public long getUpdateHeaderImageCooldown() {
        return updateHeaderImageCooldown;
    }

    /**
     * 设置
     * @param updateHeaderImageCooldown
     */
    public void setUpdateHeaderImageCooldown(long updateHeaderImageCooldown) {
        this.updateHeaderImageCooldown = updateHeaderImageCooldown;
    }

    /**
     * 获取
     * @return updateMessageCooldown
     */
    public long getUpdateMessageCooldown() {
        return updateMessageCooldown;
    }

    /**
     * 设置
     * @param updateMessageCooldown
     */
    public void setUpdateMessageCooldown(long updateMessageCooldown) {
        this.updateMessageCooldown = updateMessageCooldown;
    }

    /**
     * 获取
     * @return updatePasswordCooldown
     */
    public long getUpdatePasswordCooldown() {
        return updatePasswordCooldown;
    }

    /**
     * 设置
     * @param updatePasswordCooldown
     */
    public void setUpdatePasswordCooldown(long updatePasswordCooldown) {
        this.updatePasswordCooldown = updatePasswordCooldown;
    }

    /**
     * 获取
     * @return caffeineMessage
     */
    public long getCaffeineMessage() {
        return caffeineMessage;
    }

    /**
     * 设置
     * @param caffeineMessage
     */
    public void setCaffeineMessage(long caffeineMessage) {
        this.caffeineMessage = caffeineMessage;
    }

    /**
     * 获取
     * @return loginPasswordCooldown
     */
    public long getLoginPasswordCooldown() {
        return loginPasswordCooldown;
    }

    /**
     * 设置
     * @param loginPasswordCooldown
     */
    public void setLoginPasswordCooldown(long loginPasswordCooldown) {
        this.loginPasswordCooldown = loginPasswordCooldown;
    }

    public String toString() {
        return "UserRedisKeyDurationConfig{registerCooldown = " + registerCooldown + ", opt = " + opt + ", updateHeaderImageCooldown = " + updateHeaderImageCooldown + ", updateMessageCooldown = " + updateMessageCooldown + ", updatePasswordCooldown = " + updatePasswordCooldown + ", caffeineMessage = " + caffeineMessage + ", loginPasswordCooldown = " + loginPasswordCooldown + "}";
    }
}
