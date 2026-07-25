package com.seek.food.config.Enum;

public class MQNameKeyEnum {
    public static final String User_Exchange_Register_Fund_Queue ="${mq.name.bind.user-exchange.register-fund-queue.name}";
    public static final String User_Exchange_Delete_File_Queue ="${mq.name.bind.user-exchange.delete-file-user-queue.name}";
    public static final String User_Exchange_Delete_Fund_Queue="${mq.name.bind.user-exchange.delete-fund-queue.name}";

    public static final String Merchant_Exchange_Delete_File_Queue ="${mq.name.bind.merchant-exchange.delete-file-merchant-queue.name}";
    public static final String Merchant_Exchange_Delete_Merchant_Queue ="${mq.name.bind.merchant-exchange.delete-merchant-queue.name}";
    public static final String Merchant_Exchange_Es_Sync_Merchant_Queue ="${mq.name.bind.merchant-exchange.es-sync-merchant-queue.name}";
    public static final String Merchant_Exchange_Delete_All_Employee_Queue ="${mq.name.bind.merchant-exchange.delete-all-employee-queue.name}";
    public static final String Merchant_Exchange_Delete_All_Meal_Queue ="${mq.name.bind.merchant-exchange.delete-all-meal-queue.name}";

    public static final String Employee_Exchange_Delete_File_Queue ="${mq.name.bind.employee-exchange.delete-file-employee-queue.name}";
    public static final String Employee_Exchange_Change_Amount_Employee_Queue ="${mq.name.bind.employee-exchange.change-employee-amount-queue.name}";

    public static final String Meal_Exchange_Delete_File_Queue ="${mq.name.bind.meal-exchange.delete-file-meal-queue.name}";

    public static final String Fund_Exchange_Use_Voucher_Queue ="${mq.name.bind.fund-exchange.use-voucher-queue.name}";

    public static final String Promotion_Exchange_Register_Voucher_Connection_Queue ="${mq.name.bind.promotion-exchange.register-voucher-connection-queue.name}";

    public static final String Voucher_Exchange_Register_Order_Ack_Queue ="${mq.name.bind.voucher-exchange.order-ack-queue.name}";

    public static final String Order_Exchange_Register_Fund_Order_Record_Queue ="${mq.name.bind.order-exchange.register-fund-order-record-queue.name}";
    public static final String Order_Exchange_Rollback_Fund_Queue ="${mq.name.bind.order-exchange.rollback-fund-queue.name}";
    public static final String Order_Exchange_Rollback_Voucher_Queue ="${mq.name.bind.order-exchange.rollback-voucher-queue.name}";
    public static final String Order_Exchange_Transfer_Fund_Queue ="${mq.name.bind.order-exchange.transfer-fund-queue.name}";


    public static final String Dead_Letter_Exchange_Delete_File_Meal_Queue ="${mq.name.bind.dead-letter-exchange.delete-file-meal-impl-queue.name}";
    public static final String Dead_Letter_Exchange_Delete_All_File_Meal_Queue ="${mq.name.bind.dead-letter-exchange.delete-all-file-meal-impl-queue.name}";
    public static final String Dead_Letter_Exchange_Rollback_All_Fund_Impl_Queue ="${mq.name.bind.dead-letter-exchange.rollback-all-fund-impl-queue.name}";

    public static final String Error_Exchange_Name="${mq.name.bind.error-exchange.exchange-name}";
    public static final String Error_Queue_Name="${mq.name.bind.error-exchange.error-queue.name}";
}
