package com.seek.food.order.Service;

public interface OrderService {
    public void insertOrder(long mealId,long connectionId,int number,double lon,double lat,String deliveryAddress);
//    public void getSimple(int start,int need);
//    public void getSimpleState(int start,int need,int state);
//    public void getDetail(long orderId);
//    public void refund(long orderId);
//    public void merchantReject(long orderId);
//    public void merchantAck(long orderId);
//    public void merchantMake(long orderId);
//    public void riderAccept(long orderId);
//    public void riderAck(long orderId);
//    public void riderDelivery(long orderId);
//    public void userReceive(long orderId);
}
