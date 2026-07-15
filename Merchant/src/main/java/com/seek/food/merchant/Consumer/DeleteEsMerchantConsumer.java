package com.seek.food.merchant.Consumer;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.config.NacosConfig.Merchant.MerchantEsTableConfig;
import com.seek.food.merchant.EsRepository.MerchantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class DeleteEsMerchantConsumer {
    private final MerchantRepository merchantRepository;

    @Autowired
    public DeleteEsMerchantConsumer(MerchantRepository merchantRepository) {
        this.merchantRepository = merchantRepository;
    }


    @RabbitListener(queues = MQNameKeyEnum.Merchant_Exchange_Delete_Es_Merchant_Queue)
    public void deleteEsMerchantQueue(long merchantId){
            merchantRepository.deleteById(merchantId);
    }
}
