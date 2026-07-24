package com.seek.food.order.Controller;


import com.seek.food.dto.Common.Result;
import com.seek.food.order.Enum.RequestPathEnum;
import com.seek.food.order.Service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RequestPathEnum.Order)
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    //新增订单
    @PostMapping
    public Result<Void> order(long mealId,long merchantId,long connectionId,int number,double lon,double lat,String deliveryAddress){
        orderService.insertOrder( mealId,merchantId,connectionId,number,lon,lat,deliveryAddress);
        return Result.success();
    }
}
