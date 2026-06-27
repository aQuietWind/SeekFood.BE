package com.seek.food.gateway.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class GatewayBlackConfig {
    @Value("${gateway.self.caffeine.local.black-ip.max-size}")
    private int caffeineBlackIpSize;
    @Value("${gateway.self.caffeine.local.black-id.max-size}")
    private int caffeineBlackIdSize;
    @Value("${gateway.self.caffeine.local.black-ip.expire-time}")
    private int caffeineBlackIpExpire;
    @Value("${gateway.self.caffeine.local.black-id.expire-time}")
    private int caffeineBlackIdExpire;
    @Value("${gateway.self.black.ip.counts}")
    private int blackIpCounts;
    @Value("${gateway.self.black.id.counts}")
    private int blackIdCounts;
    @Value("${gateway.self.black.ip.duration}")
    private int blackIpDuration;
    @Value("${gateway.self.black.id.duration}")
    private int blackIdDuration;


    public GatewayBlackConfig() {
    }


    /**
     * 获取
     * @return caffeineBlackIpSize
     */
    public int getCaffeineBlackIpSize() {
        return caffeineBlackIpSize;
    }

    /**
     * 设置
     * @param caffeineBlackIpSize
     */
    public void setCaffeineBlackIpSize(int caffeineBlackIpSize) {
        this.caffeineBlackIpSize = caffeineBlackIpSize;
    }

    /**
     * 获取
     * @return caffeineBlackIdSize
     */
    public int getCaffeineBlackIdSize() {
        return caffeineBlackIdSize;
    }

    /**
     * 设置
     * @param caffeineBlackIdSize
     */
    public void setCaffeineBlackIdSize(int caffeineBlackIdSize) {
        this.caffeineBlackIdSize = caffeineBlackIdSize;
    }

    /**
     * 获取
     * @return caffeineBlackIpExpire
     */
    public int getCaffeineBlackIpExpire() {
        return caffeineBlackIpExpire;
    }

    /**
     * 设置
     * @param caffeineBlackIpExpire
     */
    public void setCaffeineBlackIpExpire(int caffeineBlackIpExpire) {
        this.caffeineBlackIpExpire = caffeineBlackIpExpire;
    }

    /**
     * 获取
     * @return caffeineBlackIdExpire
     */
    public int getCaffeineBlackIdExpire() {
        return caffeineBlackIdExpire;
    }

    /**
     * 设置
     * @param caffeineBlackIdExpire
     */
    public void setCaffeineBlackIdExpire(int caffeineBlackIdExpire) {
        this.caffeineBlackIdExpire = caffeineBlackIdExpire;
    }

    /**
     * 获取
     * @return blackIpCounts
     */
    public int getBlackIpCounts() {
        return blackIpCounts;
    }

    /**
     * 设置
     * @param blackIpCounts
     */
    public void setBlackIpCounts(int blackIpCounts) {
        this.blackIpCounts = blackIpCounts;
    }

    /**
     * 获取
     * @return blackIdCounts
     */
    public int getBlackIdCounts() {
        return blackIdCounts;
    }

    /**
     * 设置
     * @param blackIdCounts
     */
    public void setBlackIdCounts(int blackIdCounts) {
        this.blackIdCounts = blackIdCounts;
    }

    /**
     * 获取
     * @return blackIpDuration
     */
    public int getBlackIpDuration() {
        return blackIpDuration;
    }

    /**
     * 设置
     * @param blackIpDuration
     */
    public void setBlackIpDuration(int blackIpDuration) {
        this.blackIpDuration = blackIpDuration;
    }

    /**
     * 获取
     * @return blackIdDuration
     */
    public int getBlackIdDuration() {
        return blackIdDuration;
    }

    /**
     * 设置
     * @param blackIdDuration
     */
    public void setBlackIdDuration(int blackIdDuration) {
        this.blackIdDuration = blackIdDuration;
    }

    public String toString() {
        return "GatewayConfig{caffeineBlackIpSize = " + caffeineBlackIpSize + ", caffeineBlackIdSize = " + caffeineBlackIdSize + ", caffeineBlackIpExpire = " + caffeineBlackIpExpire + ", caffeineBlackIdExpire = " + caffeineBlackIdExpire + ", blackIpCounts = " + blackIpCounts + ", blackIdCounts = " + blackIdCounts + ", blackIpDuration = " + blackIpDuration + ", blackIdDuration = " + blackIdDuration + "}";
    }
}
