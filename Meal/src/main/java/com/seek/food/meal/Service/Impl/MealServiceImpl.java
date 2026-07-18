package com.seek.food.meal.Service.Impl;

import com.seek.food.config.Data.RedisKeyData;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.config.NacosConfig.MQ.MealExchangeConfig;
import com.seek.food.config.NacosConfig.Meal.MealParamsRulesConfig;
import com.seek.food.config.NacosConfig.Meal.MealRedisKeyConfig;
import com.seek.food.dto.Meal.MealDTO;
import com.seek.food.meal.Caffeine.MealCaffeine;
import com.seek.food.meal.Caffeine.MealMerchantCaffeine;
import com.seek.food.meal.Mapper.MealMapper;
import com.seek.food.meal.Service.MealService;
import com.seek.food.util.Context.TokenIdContext;
import com.seek.food.util.Redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@RefreshScope
public class MealServiceImpl implements MealService {

    private final CommonParamRulesConfig commonParamRulesConfig;
    private final MealParamsRulesConfig mealParamsRulesConfig;
    private final MealMapper mealMapper;
    private final MealRedisKeyConfig mealRedisKeyConfig;
    private final MealMerchantCaffeine mealMerchantCaffeine;
    private final MealCaffeine mealCaffeine;
    private final RabbitTemplate rabbitTemplate;
    private final MealExchangeConfig mealExchangeConfig;
    private final StringRedisTemplate stringRedisTemplate;
    public MealServiceImpl(CommonParamRulesConfig commonParamRulesConfig,MealParamsRulesConfig mealParamsRulesConfig
    ,MealMapper mealMapper,MealRedisKeyConfig mealRedisKeyConfig,MealMerchantCaffeine mealMerchantCaffeine,MealCaffeine mealCaffeine
    ,RabbitTemplate rabbitTemplate,MealExchangeConfig mealExchangeConfig,StringRedisTemplate stringRedisTemplate) {
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.mealParamsRulesConfig = mealParamsRulesConfig;
        this.mealMapper = mealMapper;
        this.mealRedisKeyConfig = mealRedisKeyConfig;
        this.mealMerchantCaffeine = mealMerchantCaffeine;
        this.mealCaffeine = mealCaffeine;
        this.mealExchangeConfig = mealExchangeConfig;
        this.rabbitTemplate = rabbitTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        //初始化mealId计数器
        stringRedisTemplate.opsForValue().setIfAbsent(mealRedisKeyConfig.getMealIdCount().getName(),""+commonParamRulesConfig.getIdCapacity());
    }


    //新增餐品
    @Override
    public void insertMeal(String mealName,double mealPrice,String mealContent){
        String tokenId=TokenIdContext.get();
        //进行格式校验
        mealParamsRulesConfig.mealNameCheck(mealName);
        mealParamsRulesConfig.mealPriceCheck(mealPrice);
        mealParamsRulesConfig.mealContextCheck(mealContent);
        //获取商家id
        long merchantId=quickGetMerchantId();
        //检查冷却期
        quickCooldown(mealRedisKeyConfig.getMealInsertCooldown(),tokenId);
        //写入DB
        mealMapper.insertMeal(mealName,mealPrice,mealContent,merchantId);
    }

    //获取预览的餐品信息
    @Override
    public List<MealDTO> getSimple(long merchantId, int start, int need){
        String tokenId=TokenIdContext.get();
        commonParamRulesConfig.merchantIdCheck(merchantId);
        commonParamRulesConfig.needNumberCheck(need);
        quickCooldown(mealRedisKeyConfig.getMealGetSimpleCooldown(),tokenId);
        return mealMapper.getSimple(merchantId,start,need);
    }

    //根据类型获取预览信息
    @Override
    public List<MealDTO> getSimpleByType(long merchantId,int type,int start,int need){
        String tokenId=TokenIdContext.get();
        commonParamRulesConfig.merchantIdCheck(merchantId);
        commonParamRulesConfig.needNumberCheck(need);
        mealParamsRulesConfig.mealTypeCheck(type);
        quickCooldown(mealRedisKeyConfig.getMealGetSimpleTypeCooldown(),tokenId);
        return mealMapper.getSimpleByType(merchantId,type,start,need);
    }

    //获取餐品详细信息
    @Override
    public MealDTO getDetail(long mealId){
        commonParamRulesConfig.commonIdCheck(mealId);
        return mealCaffeine.getAndAutoLoad(mealId,stringRedisTemplate,mealRedisKeyConfig.getMealMessageCaffeine().getRedisKey(mealId)
        ,mealRedisKeyConfig.getMealMessageCaffeine().getDuration(),MealDTO.class,k->mealMapper.getDetail(mealId));
    }

    //商家获取预览的餐品信息
    @Override
    public List<MealDTO> merchantGetSimple(int start,int need){
        long merchantId=quickGetMerchantId();
        commonParamRulesConfig.needNumberCheck(need);
        quickCooldown(mealRedisKeyConfig.getMealGetSimpleCooldown(),merchantId);
        return mealMapper.merchantGetSimple(start,need,merchantId);
    }

    //商家根据类型获取预览信息
    @Override
    public List<MealDTO> merchantGetSimpleByType(int type,int start,int need){
        long merchantId=quickGetMerchantId();
        commonParamRulesConfig.needNumberCheck(need);
        mealParamsRulesConfig.mealTypeCheck(type);
        quickCooldown(mealRedisKeyConfig.getMealGetSimpleTypeCooldown(),merchantId);
        return mealMapper.merchantGetSimpleByType(type,start,need,merchantId);
    }

    //商家获取餐品详细信息
    @Override
    public MealDTO merchantGetDetail(long mealId){

    }

    //更改餐品常规的信息
    @Override
    public void updateMessage(MealDTO meal){

    }

    //更改展示图片
    @Override
    public void updateShowImage(long mealId, MultipartFile file){

    }

    //更改价格
    @Override
    public void updatePrice(long mealId,double price){

    }

    //更改出售状态
    @Override
    public void updateSell(long mealId){

    }

    //删除餐品(进入锁定状态)
    @Override
    public void deleteMeal(long mealId){

    }

    //取消锁定状态
    @Override
    public void stopLock(long mealId){

    }

    private long quickGetMerchantId(){
        return TokenIdContext.getAndCheck(commonParamRulesConfig.getMerchantIdStart(),commonParamRulesConfig.getIdCapacity());
    }

    private void quickCooldown(RedisKeyData key,Object id){
        RedisUtil.checkCooldown(stringRedisTemplate,key.getRedisKey(id),key.getDuration());
    }
}
