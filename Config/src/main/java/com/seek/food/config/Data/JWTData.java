package com.seek.food.config.Data;

public class JWTData {
    private String secretKey;
    private String headerSign;
    private long tokenDuration;

    public JWTData() {
    }

    public JWTData(String secretKey, String headerSign, long tokenDuration) {
        this.secretKey = secretKey;
        this.headerSign = headerSign;
        this.tokenDuration = tokenDuration;
    }

    /**
     * 获取
     * @return secretKey
     */
    public String getSecretKey() {
        return secretKey;
    }

    /**
     * 设置
     * @param secretKey
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * 获取
     * @return headerSign
     */
    public String getHeaderSign() {
        return headerSign;
    }

    /**
     * 设置
     * @param headerSign
     */
    public void setHeaderSign(String headerSign) {
        this.headerSign = headerSign;
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
        return "JWTData{secretKey = " + secretKey + ", headerSign = " + headerSign + ", tokenDuration = " + tokenDuration + "}";
    }
}
