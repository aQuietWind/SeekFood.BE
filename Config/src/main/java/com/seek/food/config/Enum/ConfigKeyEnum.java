package com.seek.food.config.Enum;

public class ConfigKeyEnum {
    public static final String Common_Redis_Key="common.redis.key.name";
    public static final String Common_Param_Rules_Key="common.param.rules";
    public static final String JWT_Config="common.jwt";

    public static final String Gateway_Black_Config="gateway.self.black";
    public static final String Gateway_Redis_Key_Config="gateway.self.redis.key.name";
    public static final String Gateway_Request_Path_Config="gateway.self.request.path";

    public static final String User_Params_Rules_Config="user.self.params.rules";
    public static final String User_Redis_Key_Config="user.self.redis.key";
    public static final String User_Caffeine_Config="user.self.jvm-caffeine";
    public static final String User_Redis_Stream_Config="user.self.redis.stream";

    public static final String Merchant_Params_Rules_Config="merchant.self.params.rules";
    public static final String Merchant_Redis_Key_Config="merchant.self.redis.key";
    public static final String Merchant_Caffeine_Config="merchant.self.jvm-caffeine";
    public static final String Merchant_Redis_Stream_Config="merchant.self.redis.stream";
    public static final String Merchant_Es_Table_Config="merchant.self.es.merchant";


    public static final String User_Exchange_Config="mq.name.bind.user-exchange";
    public static final String Merchant_Exchange_Config="mq.name.bind.merchant-exchange";

    public static final String Fund_Params_Rules_Config="fund.self.params.rules";
    public static final String Fund_Redis_Key_Duration_Config="fund.self.redis.key.duration";
    public static final String Fund_Redis_Key_Name_Config="fund.self.redis.key.name";
//    public static final String =;
//    public static final String =;
}
