package com.seek.food.merchant.Consumer;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.config.NacosConfig.Merchant.MerchantEsTableConfig;
import com.seek.food.config.NacosConfig.Merchant.MerchantRedisKeyConfig;
import com.seek.food.dto.Merchant.MerchantEsDTO;
import com.seek.food.merchant.EsRepository.MerchantRepository;
import com.seek.food.merchant.Mapper.MerchantMapper;
import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import com.seek.food.util.Redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@Slf4j
public class EsSyncMerchantConsumer {
    private final StringRedisTemplate stringRedisTemplate;
    private final MerchantMapper merchantMapper;
    private final MerchantRedisKeyConfig merchantRedisKeyConfig;
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticsearchConverter elasticsearchConverter;

    @Autowired
    public EsSyncMerchantConsumer(StringRedisTemplate stringRedisTemplate
    , MerchantMapper merchantMapper, MerchantRedisKeyConfig merchantRedisKeyConfig, CommonParamRulesConfig commonParamRulesConfig
    , ElasticsearchOperations elasticsearchOperations, ElasticsearchConverter elasticsearchConverter) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.merchantMapper = merchantMapper;
        this.merchantRedisKeyConfig = merchantRedisKeyConfig;
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticsearchConverter = elasticsearchConverter;
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
        if (merchant.getMerchantLocation().equals(",")) merchant.setMerchantLocation(null);
        System.err.println(merchant.getMerchantLocation());
        //尝试同步
        try {
            updateOnly(merchant);
        }catch (Exception e){throw new BizException(ErrorCodeEnum.SERVER_ERROR);}
        finally {
            //无论失败成功都更改状态，防止出现永远不更新的问题
            ackState(merchantId);
        }
    }

    //快速修改状态
    private void ackState(long merchantId){
        RedisUtil.oftenSetBit(stringRedisTemplate,merchantRedisKeyConfig.getMerchantEsSyncRecord().getName(),merchantId,true,commonParamRulesConfig.getIdCapacity());
    }


    public void updateOnly(MerchantEsDTO merchant) {
        // DTO转Map，自动识别@Field、全局下划线策略
        Document sourceMap = elasticsearchConverter.mapObject(merchant);
        // 构建更新对象，全量覆盖_source
        UpdateQuery updateQuery = UpdateQuery.builder(String.valueOf(merchant.getMerchantId()))
                .withDocument(sourceMap)
                // 核心配置：不存在时不创建文档
                .withDocAsUpsert(false)
                .build();
        // 执行更新，无文档则无任何写入
        elasticsearchOperations.update(updateQuery);
    }












}
