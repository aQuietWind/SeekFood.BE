package com.seek.food.dto.Fund;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundOrderRefundRecordDTO {
    private Long recordId;
    private Long accountId;
    private Long orderId;
    private String orderRefundDescription;
    private Double refundCost;
    private Integer refundType;
    private LocalDateTime createTime;
}
