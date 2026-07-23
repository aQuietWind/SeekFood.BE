package com.seek.food.promotion.Controller;

import com.seek.food.dto.Common.Result;
import com.seek.food.dto.Promotion.MerchantLoginPromotionDTO;
import com.seek.food.promotion.Enum.RequestPathEnum;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(RequestPathEnum.Merchant_Login_Promotion)
@RestController
public class MerchantLoginPromotionController {

    @PostMapping
    public Result<MerchantLoginPromotionDTO> insertPromotion(MerchantLoginPromotionDTO merchantLoginPromotionDTO) {

        return Result.success();
    }
}
