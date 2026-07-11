package com.seek.food.merchant.Controller;

import com.seek.food.dto.Common.Result;
import com.seek.food.dto.Merchant.MerchantDTO;
import com.seek.food.merchant.Enum.RequestPathEnum;
import com.seek.food.merchant.Service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RequestPathEnum.Merchant)
public class MerchantController {
    private final MerchantService merchantService;
    @Autowired
    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }
    @GetMapping(RequestPathEnum.Merchant_Detail)
    public Result<MerchantDTO> getMerchantDetail(long merchantId) {
        return Result.success(merchantService.getMerchantDetail(merchantId));
    }
    @GetMapping(RequestPathEnum.Merchant_Self)
    public Result<MerchantDTO> getMerchantSelf(){
        return Result.success(merchantService.getMerchantSelf());
    }
}
