package com.seek.food.merchant.Service;

import com.seek.food.dto.Merchant.MerchantDTO;

public interface MerchantService {
    public MerchantDTO getMerchantDetail(long merchantId);
    public MerchantDTO getMerchantSelf();
}
