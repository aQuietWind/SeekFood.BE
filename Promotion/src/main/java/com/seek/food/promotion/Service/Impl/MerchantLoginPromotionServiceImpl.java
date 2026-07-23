package com.seek.food.promotion.Service.Impl;

import com.seek.food.dto.Promotion.MerchantLoginPromotionDTO;
import com.seek.food.promotion.Service.MerchantLoginPromotionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RefreshScope
@Slf4j
public class MerchantLoginPromotionServiceImpl implements MerchantLoginPromotionService {

    //新增活动
    @Override
    public void insertPromotion(MerchantLoginPromotionDTO merchantLoginPromotionDTO){
        
    }

    //获取某商家的简易活动介绍
    @Override
    public List<MerchantLoginPromotionDTO> getSimple(int start, int need, long merchantId){

    }

    //获取某商家还在有效期的简易活动介绍
    @Override
    public List<MerchantLoginPromotionDTO> getSimpleEffective(int start, int need,long merchantId){

    }

    //获取详细的活动信息
    @Override
    public MerchantLoginPromotionDTO getDetail(long promotionId){

    }

    //通过活动获取优惠券
    @Override
    public void getVoucher(long promotionId){

    }
}
