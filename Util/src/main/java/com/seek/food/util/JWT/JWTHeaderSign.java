package com.seek.food.util.JWT;

import java.util.ArrayList;
import java.util.Map;

public class JWTHeaderSign {

    private String headerSign;
    private String secretKey;

    public JWTHeaderSign() {
    }
    //适配JWTConfig和JWTUtil
    public static JWTHeaderSign[] getHeaderSignArr(Map<String,String> secretKey, Map<String,String> headerSign, String headerSeparator
    , ArrayList<String> name){
        JWTHeaderSign[] jwtHeaderSignArr=new JWTHeaderSign[name.size()];
        for(int i=0;i<name.size();i++){
            //封装
            jwtHeaderSignArr[i]=new JWTHeaderSign(headerSign.get(name.get(i)),secretKey.get(name.get(i)));
        }
        return jwtHeaderSignArr;
    }

    public JWTHeaderSign(String headerSign, String secretKey) {
        this.headerSign = headerSign;
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

    public String toString() {
        return "JWTHeaderSign{headerSign = " + headerSign + ", secretKey = " + secretKey + "}";
    }
}
