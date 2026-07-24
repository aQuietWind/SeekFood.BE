package com.seek.food.order.Controller;


import com.seek.food.dto.Common.Result;
import com.seek.food.order.Enum.RequestPathEnum;
import com.seek.food.order.Service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
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
    @GlobalTransactional
    public Result<Void> insertOrder(long mealId,Long connectionId,int number,double lon,double lat,String deliveryAddress){
        orderService.insertOrder( mealId,connectionId,number,lon,lat,deliveryAddress);
        return Result.success();
    }
}
