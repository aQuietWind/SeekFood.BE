package com.seek.food.merchant.Consumer;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.seek.food.config.Data.RedisStreamData;
import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.config.NacosConfig.Merchant.MerchantEsTableConfig;
import com.seek.food.config.NacosConfig.Merchant.MerchantRedisStreamConfig;
import com.seek.food.util.Es.EsUtil;
import com.seek.food.util.FileUtil.FileRemove;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class DeleteEsMerchantConsumer {
    private final ElasticsearchClient elasticsearchClient;
    private final MerchantEsTableConfig merchantEsTableConfig;
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    public DeleteEsMerchantConsumer(StringRedisTemplate stringRedisTemplate, ElasticsearchClient elasticsearchClient, MerchantEsTableConfig merchantEsTableConfig) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.elasticsearchClient = elasticsearchClient;
        this.merchantEsTableConfig = merchantEsTableConfig;
    }


    @RabbitListener(queues = MQNameKeyEnum.Merchant_Exchange_Delete_Es_Merchant_Queue)
    public void deleteEsMerchantQueue(long merchantId){
        try {
            EsUtil.quickDelete(elasticsearchClient,merchantEsTableConfig.getIndexName(),merchantId);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
    }
}
