package com.seek.food.dto.Merchant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantEsDTO {
    private Long merchant_id;
    private String merchant_name;
    private Integer merchant_collect_amount;
    private Integer merchant_order_amount;
    private String merchant_home_image;
    private Boolean is_open;

    public MerchantDTO esToMerchantDTO(){
        MerchantDTO merchantDTO = new MerchantDTO();
        merchantDTO.setMerchantId(merchant_id);
        merchantDTO.setMerchantName(merchant_name);
        merchantDTO.setMerchantCollectAmount(merchant_collect_amount);
        merchantDTO.setMerchantOrderAmount(merchant_order_amount);
        merchantDTO.setMerchantHomeImageAddr(merchant_home_image);
        merchantDTO.setOpen(is_open);
        return merchantDTO;
    }
}
