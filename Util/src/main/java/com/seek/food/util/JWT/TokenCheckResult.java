package com.seek.food.util.JWT;

public class TokenCheckResult {
    String token;
    long resultId;

    public TokenCheckResult() {
    }

    public TokenCheckResult(String token, long resultId) {
        this.token = token;
        this.resultId = resultId;
    }

    /**
     * 获取
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取
     * @return resultId
     */
    public long getResultId() {
        return resultId;
    }

    /**
     * 设置
     * @param resultId
     */
    public void setResultId(long resultId) {
        this.resultId = resultId;
    }

    public String toString() {
        return "TokenCheckResult{token = " + token + ", resultId = " + resultId + "}";
    }
}