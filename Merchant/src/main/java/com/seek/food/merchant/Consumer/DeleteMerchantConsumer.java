package com.seek.food.merchant.Consumer;

import com.seek.food.config.Enum.MQNameKeyEnum;
import com.seek.food.config.NacosConfig.Merchant.MerchantParamsRulesConfig;
import com.seek.food.dto.Merchant.MerchantDTO;
import com.seek.food.merchant.Mapper.MerchantMapper;
import com.seek.food.util.FileUtil.FileRemove;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class DeleteMerchantConsumer {
    private final MerchantMapper merchantMapper;
    private final MerchantParamsRulesConfig merchantParamsRulesConfig;

    @Autowired
    public DeleteMerchantConsumer(MerchantMapper merchantMapper, MerchantParamsRulesConfig merchantParamsRulesConfig) {
        this.merchantMapper = merchantMapper;
        this.merchantParamsRulesConfig = merchantParamsRulesConfig;
    }


    @RabbitListener(queues = MQNameKeyEnum.Merchant_Exchange_Delete_Merchant_Queue)
    public void deleteMerchantQueue(long merchantId){
        MerchantDTO merchant=merchantMapper.getDeleteMerchant(merchantId);
        //删除店主照片
        FileRemove.removeFileOutError(merchantParamsRulesConfig.getMasterImageDest(),merchant.getMerchantMasterImageAddr());
        //删除封面照片
        FileRemove.removeFileOutError(merchantParamsRulesConfig.getHomeImageDest(),merchant.getMerchantMasterImageAddr());
        //删除证明照片
        FileRemove.removeFileArrayOutError(merchantParamsRulesConfig.getProofImageDest(),merchant.getMerchantProofImageAddr().split(","));
        //删除展示照片
        FileRemove.removeFileArrayOutError(merchantParamsRulesConfig.getShowImageDest(),merchant.getMerchantShowImageAddr().split(","));
    }











}
