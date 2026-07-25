package com.seek.food.order.Mapper;

import com.seek.food.dto.Order.OrderDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    public void insertOrder(OrderDTO order);
    public boolean lock(long orderId);
}
