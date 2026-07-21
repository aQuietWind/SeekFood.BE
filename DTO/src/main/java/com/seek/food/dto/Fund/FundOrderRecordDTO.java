package com.seek.food.dto.Fund;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundOrderRecordDTO {
    private Long recordId;
    private Long accountId;
    private Long orderId;
    private String orderDescription;
    private Double cost;
    private LocalDateTime deadline;
    private LocalDateTime ableRollbackTime;
    private LocalDateTime createTime;
    private Boolean pay;
    private Boolean rollback;
    private Boolean refund;
}
