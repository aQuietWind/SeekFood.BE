package com.seek.food.order.Mapper;

import com.seek.food.dto.Order.OrderDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    public void insertOrder(OrderDTO order);
    public boolean lock(long orderId);
    public boolean ack(long orderId);
    public List<OrderDTO> getSimple(long tokenId);
    public void getSimpleState(int start,int need,int state,long tokenId);
    public void getDetail(long orderId,long tokenId);
    public void refund(long orderId,long userId);
    public void merchantReject(long orderId,long merchantId);
    public void merchantAck(long orderId,long merchantId);
    public void merchantMake(long orderId,long merchantId);
    public void riderAccept(long orderId,long riderId);
    public void riderAck(long orderId,long riderId);
    public void riderDelivery(long orderId,long riderId);
    public void userReceive(long orderId,long userId);
}
