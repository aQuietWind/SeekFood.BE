package com.seek.food.dto.Merchant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantEsDTO {
    private Long merchantId;
    private String merchantName;
    private Integer merchantCollectAmount;
    private Integer merchantOrderAmount;
    private String merchantHomeImage;
    private String merchantLocation;
    @Field("is_open")
    private Boolean open;
}
