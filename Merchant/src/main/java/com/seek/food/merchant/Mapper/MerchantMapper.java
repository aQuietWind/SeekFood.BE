package com.seek.food.merchant.Mapper;

import com.seek.food.dto.Merchant.MerchantDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MerchantMapper {
    public MerchantDTO getMerchantById(long merchantId);
}
