package com.seek.food.order.Service.Impl;

import com.seek.food.config.Data.RedisKeyData;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.config.NacosConfig.MQ.OrderExchangeConfig;
import com.seek.food.config.NacosConfig.Order.OrderParamsRulesConfig;
import com.seek.food.config.NacosConfig.Order.OrderRedisKeyConfig;
import com.seek.food.dto.Fund.FundOrderRecordMQDTO;
import com.seek.food.dto.Meal.MealDTO;
import com.seek.food.dto.Order.OrderDTO;
import com.seek.food.order.Feign.MealClient;
import com.seek.food.order.Feign.MerchantClient;
import com.seek.food.order.Feign.VoucherClient;
import com.seek.food.order.Mapper.OrderMapper;
import com.seek.food.order.Service.OrderService;
import com.seek.food.util.CommonUtil.IdUtil;
import com.seek.food.util.Context.TokenIdContext;
import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import com.seek.food.util.MQ.MQUtil;
import com.seek.food.util.Redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
@Slf4j
public class OrderServicerImpl implements OrderService {


    private final CommonParamRulesConfig commonParamRulesConfig;
    private final StringRedisTemplate stringRedisTemplate;
    private final OrderRedisKeyConfig orderRedisKeyConfig;
    private final MealClient mealClient;
    private final VoucherClient voucherClient;
    private final MerchantClient merchantClient;
    private final RedissonClient redissonClient;
    private final OrderParamsRulesConfig orderParamsRulesConfig;
    private final OrderMapper orderMapper;
    private final OrderExchangeConfig orderExchangeConfig;
    private final RabbitTemplate rabbitTemplate;

    public OrderServicerImpl(CommonParamRulesConfig commonParamRulesConfig, StringRedisTemplate stringRedisTemplate
            , OrderRedisKeyConfig orderRedisKeyConfig, MealClient mealClient, VoucherClient voucherClient, MerchantClient merchantClient, RedissonClient redissonClient, OrderParamsRulesConfig orderParamsRulesConfig, OrderMapper orderMapper, OrderExchangeConfig orderExchangeConfig, RabbitTemplate rabbitTemplate) {
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.stringRedisTemplate = stringRedisTemplate;
        this.orderRedisKeyConfig = orderRedisKeyConfig;
        this.mealClient = mealClient;
        stringRedisTemplate.opsForValue().setIfAbsent(orderRedisKeyConfig.getOrderIdCount().getName(),""+commonParamRulesConfig.getIdCapacity());
        this.voucherClient = voucherClient;
        this.merchantClient = merchantClient;
        this.redissonClient = redissonClient;
        this.orderParamsRulesConfig = orderParamsRulesConfig;
        this.orderMapper = orderMapper;
        this.orderExchangeConfig = orderExchangeConfig;
        this.rabbitTemplate = rabbitTemplate;
    }

    //新增订单
    @Override
    public void insertOrder(long mealId,Long connectionId,int number,double lon,double lat,String deliveryAddress){
        commonParamRulesConfig.commonIdCheck(mealId);
        if (connectionId!=null)commonParamRulesConfig.commonIdCheck(connectionId);
        //获取用户id
        long userId=quickGetUserId();
        //上锁
        RLock lock=redissonClient.getLock(orderRedisKeyConfig.getOrderInsertLock().getRedisKey(userId));
        if (!lock.tryLock()) throw new BizException(ErrorCodeEnum.REQUEST_IN_COOLDOWN);
        try {
            //获取餐品信息
            MealDTO meal = mealClient.mealGetDetail(mealId).getData();
            if (meal == null) throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
            //获取运送距离
            Long distance = merchantClient.merchantGetDistance(lon, lat, meal.getMerchantId()).getData();
            System.err.println(distance);
            if (distance == null) throw new BizException(ErrorCodeEnum.DATA_NOT_RIGHT);
            //检查优惠券
            Double discountCost=0.0;
            if (connectionId!=null) {
                discountCost = voucherClient.connectionCheck(connectionId, connectionId).getData();
                if (discountCost == null) throw new BizException(ErrorCodeEnum.CONDITION_NOT_PASS);
            }
            OrderDTO order=OrderDTO.quickGet(
                    IdUtil.IdGenerateByIncrease(orderRedisKeyConfig.getOrderIdCount().getName(),stringRedisTemplate)
                    ,userId,meal,connectionId,number,lon,lat,deliveryAddress,discountCost,orderParamsRulesConfig.distanceCost(distance));
            //锁定优惠券
            if (connectionId!=null)voucherClient.connectionLock(connectionId, order.getOrderId());
            //新增该订单
            orderMapper.insertOrder(order);
            //发送至Fund处新增订单记录，然后等待支付
            MQUtil.send(orderExchangeConfig.getExchangeName(),orderExchangeConfig.getRegisterFundOrderRecordQueue().getRoutingKey()
            , new FundOrderRecordMQDTO(null, order.getOrderId(), userId,order.getTotalCost()),rabbitTemplate );
        }finally {
            //解锁
            lock.unlock();
        }
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

}
