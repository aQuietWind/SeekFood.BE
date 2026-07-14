package com.seek.food.merchant.Consumer;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.config.NacosConfig.Merchant.MerchantEsTableConfig;
import com.seek.food.config.NacosConfig.Merchant.MerchantRedisKeyConfig;
import com.seek.food.dto.Merchant.MerchantEsDTO;
import com.seek.food.merchant.Mapper.MerchantMapper;
import com.seek.food.util.Es.EsUtil;
import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import com.seek.food.util.Redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class EsSyncMerchantConsumer {
    private final StringRedisTemplate stringRedisTemplate;
    private final MerchantEsTableConfig merchantEsTableConfig;
    private final ElasticsearchClient esClient;
    private final MerchantMapper merchantMapper;
    private final MerchantRedisKeyConfig merchantRedisKeyConfig;
    private final CommonParamRulesConfig commonParamRulesConfig;

    @Autowired
    public EsSyncMerchantConsumer(StringRedisTemplate stringRedisTemplate, MerchantEsTableConfig merchantEsTableConfig, ElasticsearchClient esClient
    , MerchantMapper merchantMapper, MerchantRedisKeyConfig merchantRedisKeyConfig, CommonParamRulesConfig commonParamRulesConfig) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.merchantEsTableConfig = merchantEsTableConfig;
        this.esClient = esClient;
        this.merchantMapper = merchantMapper;
        this.merchantRedisKeyConfig = merchantRedisKeyConfig;
        this.commonParamRulesConfig = commonParamRulesConfig;
    }


    @RabbitListener(queues = MQNameKeyEnum.Merchant_Exchange_Es_Sync_Merchant_Queue)
    public void esSyncMerchantQueue(long merchantId){
        MerchantEsDTO merchant=merchantMapper.getEsMerchant(merchantId);
        //校验该商家是否已经删除
        if (merchant==null) {
            ackState(merchantId);
            return;
        }
        //检验是否为空
        if (merchant.getMerchant_location().equals(",")) merchant.setMerchant_location(null);
        System.err.println(merchant.getMerchant_location());
        //尝试同步
        try {
            EsUtil.quickUpdate(esClient,merchantEsTableConfig.getIndexName(),merchantId,merchant);
        }catch (Exception e){
            throw new BizException(ErrorCodeEnum.SERVER_ERROR);
        }
        finally {
            //无论失败成功都更改状态，防止出现永远不更新的问题
            ackState(merchantId);
        }
    }

    private void ackState(long merchantId){
        RedisUtil.oftenSetBit(stringRedisTemplate,merchantRedisKeyConfig.getMerchantEsSyncRecord().getName()
                ,merchantId,true,commonParamRulesConfig.getIdCapacity());
    }
















}
