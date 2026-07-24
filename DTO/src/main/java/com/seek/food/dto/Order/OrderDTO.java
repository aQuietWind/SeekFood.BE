package com.seek.food.dto.Order;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long orderId;
    private Long userId;
    private Long merchantId;
    private Long riderId;
    private Long voucherId;
    private Long mealId;
    private String mealName;
    private Double mealPrice;
    private String mealDescription;
    private String mealShowImageAddr;
    private String mealContent;
    private Integer mealType;
    private Integer number;
    private String deliveryAddress;
    private Double deliveryLon;
    private Double deliveryLat;
    private Double originCost;
    private Double discountCost;
    private Double riderCost;
    private Double totalCost;
    private LocalDateTime createTime;
    private LocalDateTime completeTime;
    private Integer stateNow;
    private Boolean refund;
    private Boolean lock;
    private Boolean ack;
    private Boolean complete;
}
