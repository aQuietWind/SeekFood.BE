package com.seek.food.config.Enum;

public class MQNameKeyEnum {
    public static final String User_Exchange_Register_Fund_Queue ="${mq.name.bind.user-exchange.register-fund-queue.name}";
    public static final String User_Exchange_Delete_File_Queue ="${mq.name.bind.user-exchange.delete-file-user-queue.name}";
    public static final String User_Exchange_Delete_Fund_Queue="${mq.name.bind.user-exchange.delete-fund-queue.name}";

    public static final String Merchant_Exchange_Delete_File_Queue ="${mq.name.bind.merchant-exchange.delete-file-merchant-queue.name}";
    public static final String Merchant_Exchange_Delete_Merchant_Queue ="${mq.name.bind.merchant-exchange.delete-merchant-queue.name}";
    public static final String Merchant_Exchange_Es_Sync_Merchant_Queue ="${mq.name.bind.merchant-exchange.es-sync-merchant-queue.name}";

    public static final String Error_Exchange_Name="${mq.name.bind.error-exchange.exchange-name}";
    public static final String Error_Queue_Name="${mq.name.bind.error-exchange.error-queue.name}";
}
