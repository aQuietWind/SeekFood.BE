package com.seek.food.dto.Fund;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//作为MQ转输的中间类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundOrderRecordMQDTO {
    private Long recordId;
    private Long orderId;
    private Long accountId;
    private Double cost;
}
