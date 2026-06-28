package com.seek.food.config.Data;


public class GatewayIpIdBlackData {
    private int caffeineMaxSize;
    private int caffeineExpireTime;
    private int counts;
    private int duration;

    public GatewayIpIdBlackData() {
    }

    public GatewayIpIdBlackData(int caffeineMaxSize, int caffeineExpireTime, int counts, int duration) {
        this.caffeineMaxSize = caffeineMaxSize;
        this.caffeineExpireTime = caffeineExpireTime;
        this.counts = counts;
        this.duration = duration;
    }

    /**
     * 获取
     * @return caffeineMaxSize
     */
    public int getCaffeineMaxSize() {
        return caffeineMaxSize;
    }

    /**
     * 设置
     * @param caffeineMaxSize
     */
    public void setCaffeineMaxSize(int caffeineMaxSize) {
        this.caffeineMaxSize = caffeineMaxSize;
    }

    /**
     * 获取
     * @return caffeineExpireTime
     */
    public int getCaffeineExpireTime() {
        return caffeineExpireTime;
    }

    /**
     * 设置
     * @param caffeineExpireTime
     */
    public void setCaffeineExpireTime(int caffeineExpireTime) {
        this.caffeineExpireTime = caffeineExpireTime;
    }

    /**
     * 获取
     * @return counts
     */
    public int getCounts() {
        return counts;
    }

    /**
     * 设置
     * @param counts
     */
    public void setCounts(int counts) {
        this.counts = counts;
    }

    /**
     * 获取
     * @return duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * 设置
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String toString() {
        return "GatewayIpIdBlackData{caffeineMaxSize = " + caffeineMaxSize + ", caffeineExpireTime = " + caffeineExpireTime + ", counts = " + counts + ", duration = " + duration + "}";
    }
}
