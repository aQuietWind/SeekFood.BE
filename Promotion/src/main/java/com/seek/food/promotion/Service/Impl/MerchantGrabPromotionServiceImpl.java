package com.seek.food.promotion.Service.Impl;

import com.seek.food.config.Data.RedisKeyData;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.config.NacosConfig.Promotion.PromotionParamsRulesConfig;
import com.seek.food.config.NacosConfig.Promotion.PromotionRedisKeyConfig;
import com.seek.food.dto.Promotion.MerchantGrabPromotionDTO;
import com.seek.food.dto.Promotion.MerchantLoginPromotionDTO;
import com.seek.food.promotion.Caffeine.MerchantGrabPromotionCaffeine;
import com.seek.food.promotion.Caffeine.MerchantLoginPromotionCaffeine;
import com.seek.food.promotion.Mapper.MerchantGrabPromotionMapper;
import com.seek.food.promotion.Service.MerchantGrabPromotionService;
import com.seek.food.promotion.Service.MerchantLoginPromotionService;
import com.seek.food.util.CommonUtil.IdUtil;
import com.seek.food.util.Context.TokenIdContext;
import com.seek.food.util.Redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RefreshScope
@Slf4j
public class MerchantGrabPromotionServiceImpl implements MerchantGrabPromotionService {


    private final PromotionRedisKeyConfig promotionRedisKeyConfig;
    private final StringRedisTemplate stringRedisTemplate;
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final MerchantGrabPromotionMapper merchantGrabPromotionMapper;
    private final MerchantGrabPromotionCaffeine merchantGrabPromotionCaffeine;
    private final PromotionParamsRulesConfig promotionParamsRulesConfig;

    public MerchantGrabPromotionServiceImpl(PromotionRedisKeyConfig promotionRedisKeyConfig, StringRedisTemplate stringRedisTemplate
            , CommonParamRulesConfig commonParamRulesConfig, MerchantGrabPromotionMapper merchantGrabPromotionMapper
            , MerchantGrabPromotionCaffeine merchantGrabPromotionCaffeine, PromotionParamsRulesConfig promotionParamsRulesConfig) {
        this.promotionRedisKeyConfig = promotionRedisKeyConfig;
        this.stringRedisTemplate = stringRedisTemplate;
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.merchantGrabPromotionMapper = merchantGrabPromotionMapper;
        this.merchantGrabPromotionCaffeine = merchantGrabPromotionCaffeine;
        this.promotionParamsRulesConfig = promotionParamsRulesConfig;
        //初始化id计数器，如果觉得写这里不符合工业化方针，可以写到@Init里
        stringRedisTemplate.opsForValue().setIfAbsent(promotionRedisKeyConfig.getMerchantGrabPromotionIdCount().getName(),""+commonParamRulesConfig.getIdCapacity());
    }

    //新增活动
    @Override
    public void insertPromotion(MerchantGrabPromotionDTO promotion){
        //依旧检查参数格式，如果觉得这里代码繁琐，可以封装到实体类或者配置类的方法中
        promotionParamsRulesConfig.promotionTitleCheck(promotion.getPromotionTitle());
        promotionParamsRulesConfig.promotionDescriptionCheck(promotion.getPromotionDescription());
        promotionParamsRulesConfig.promotionNoticeCheck(promotion.getPromotionNotice());
        promotionParamsRulesConfig.durationDayCheck(promotion.getStartTime(), promotion.getEndTime());
        promotionParamsRulesConfig.grabAmountCheck(promotion.getVoucherOriginAmount());
        //获取商家id
        long merchantId=quickGetMerchantId();
        //检查冷却
        quickCooldown(promotionRedisKeyConfig.getMerchantGrabPromotionInsertCooldown(),merchantId);
        //放入必要数据
        promotion.setMerchantId(merchantId);
        promotion.setPromotionId(IdUtil.IdGenerateByIncrease(promotionRedisKeyConfig.getMerchantGrabPromotionIdCount().getName(),stringRedisTemplate));
        //插入数据
        merchantGrabPromotionMapper.insertPromotion(promotion);
    }

    //获取某商家的简易活动介绍
    @Override
    public List<MerchantGrabPromotionDTO> getSimple(int start, int need, long merchantId){
        //检查参数格式
        commonParamRulesConfig.needNumberCheck(need);
        commonParamRulesConfig.merchantIdCheck(merchantId);
        //检查冷却，防止脚本频繁从DB中获取数据,因为正常情况下前端恰好可以通过节流等操作控制间隔时间，所以1~2秒冷却对于正常用户使用是不会有任何影响的
        quickCooldown(promotionRedisKeyConfig.getMerchantGrabPromotionGetSimpleCooldown(),quickGetStringId());
        //返回数据
        return merchantGrabPromotionMapper.getSimple(start, need, merchantId);
    }

    //获取某商家还在有效期的简易活动介绍
    @Override
    public List<MerchantGrabPromotionDTO> getSimpleEffective(int start, int need,long merchantId){
        //检查参数格式
        commonParamRulesConfig.needNumberCheck(need);
        commonParamRulesConfig.merchantIdCheck(merchantId);
        //检查冷却，防止脚本频繁从DB中获取数据,因为正常情况下前端恰好可以通过节流等操作控制间隔时间，所以1~2秒冷却对于正常用户使用是不会有任何影响的
        quickCooldown(promotionRedisKeyConfig.getMerchantGrabPromotionGetSimpleEffectiveCooldown(),quickGetStringId());
        //返回数据
        return merchantGrabPromotionMapper.getSimple(start, need, merchantId);
    }

    //获取详细的活动信息
    @Override
    public MerchantGrabPromotionDTO getDetail(long promotionId){
        //检查参数格式
        commonParamRulesConfig.commonIdCheck(promotionId);
        return merchantGrabPromotionCaffeine.getAndAutoLoad(promotionId
                ,stringRedisTemplate
                ,promotionRedisKeyConfig.getMerchantGrabPromotionMessageCaffeine().getRedisKey(promotionId)
                ,promotionRedisKeyConfig.getMerchantGrabPromotionMessageCaffeine().getDuration()
                ,MerchantGrabPromotionDTO.class,k->merchantGrabPromotionMapper.getDetail(promotionId));
    }

    //通过活动获取优惠券
    @Override
    public void getVoucher(long promotionId){

    }

    private void quickCooldown(RedisKeyData key, Object id){
        RedisUtil.checkCooldown(stringRedisTemplate,key.getRedisKey(id),key.getDuration());
    }

    private long quickGetMerchantId(){
        return TokenIdContext.getAndCheck(commonParamRulesConfig.getMerchantIdStart(),commonParamRulesConfig.getIdCapacity());
    }

    private long quickGetUserId(){
        return TokenIdContext.getAndCheck(commonParamRulesConfig.getUserIdStart(),commonParamRulesConfig.getIdCapacity());
    }

    private String quickGetStringId(){
        return TokenIdContext.get();
    }
}
