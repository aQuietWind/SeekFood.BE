package com.seek.food.interaction.Service.Impl;

import com.seek.food.config.Data.QueueData;
import com.seek.food.config.Data.RedisKeyData;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.config.NacosConfig.Interaction.InteractionParamsRulesConfig;
import com.seek.food.config.NacosConfig.Interaction.InteractionRedisKeyConfig;
import com.seek.food.config.NacosConfig.MQ.InteractionExchangeConfig;
import com.seek.food.dto.Common.ChangeAmountDTO;
import com.seek.food.interaction.Service.LikeService;
import com.seek.food.util.Context.TokenIdContext;
import com.seek.food.util.MQ.MQUtil;
import com.seek.food.util.Redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RefreshScope
public class LikeServiceImpl implements LikeService {


    private final StringRedisTemplate stringRedisTemplate;
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final InteractionParamsRulesConfig interactionParamsRulesConfig;
    private final InteractionRedisKeyConfig interactionRedisKeyConfig;
    private final InteractionExchangeConfig interactionExchangeConfig;
    private final RabbitTemplate rabbitTemplate;

    public LikeServiceImpl(StringRedisTemplate stringRedisTemplate, CommonParamRulesConfig commonParamRulesConfig, InteractionParamsRulesConfig interactionParamsRulesConfig, InteractionRedisKeyConfig interactionRedisKeyConfig, InteractionExchangeConfig interactionExchangeConfig, RabbitTemplate rabbitTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.interactionParamsRulesConfig = interactionParamsRulesConfig;
        this.interactionRedisKeyConfig = interactionRedisKeyConfig;
        this.interactionExchangeConfig = interactionExchangeConfig;
        this.rabbitTemplate = rabbitTemplate;
    }

    //点赞商家
    @Override
    public Boolean likeMerchant(long merchantId,boolean value){
        Boolean result = setBitMap(merchantId,interactionRedisKeyConfig.getInteractionLikeMerchant(),quickGetTokenId(),value);
        //发送消息到同步数目与状态分别两个MQ（两个MQ使用相同的routingKey）
        if(result)quickSend(interactionExchangeConfig.getChangeMerchantLikeAmountQueue(),new ChangeAmountDTO(merchantId,value));
        return result;
    }





    private String quickGetTokenId(){
        return TokenIdContext.get();
    }

    private Boolean setBitMap(long aimId, RedisKeyData key,Object accountId, boolean value){
        return RedisUtil.oftenSetBitWithPerX(stringRedisTemplate,key.getRedisKey(accountId),aimId,value,commonParamRulesConfig.getIdCapacity(),
                interactionParamsRulesConfig.getBitmapPerXNumber());
    }

    private void quickSend(QueueData queue, Object message){
        MQUtil.send(interactionExchangeConfig.getExchangeName(),queue.getRoutingKey(),message,rabbitTemplate);
    }







}
