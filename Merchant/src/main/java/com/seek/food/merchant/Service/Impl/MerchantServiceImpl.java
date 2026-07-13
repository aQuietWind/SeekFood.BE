package com.seek.food.merchant.Service.Impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.config.NacosConfig.MQ.MerchantExchangeConfig;
import com.seek.food.config.NacosConfig.Merchant.*;
import com.seek.food.dto.Merchant.MerchantDTO;
import com.seek.food.merchant.Caffeine.MerchantCaffeine;
import com.seek.food.merchant.Mapper.MerchantMapper;
import com.seek.food.merchant.Service.MerchantService;
import com.seek.food.util.Context.TokenIdContext;
import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import com.seek.food.util.FileUtil.FileSave;
import com.seek.food.util.MQ.MQUtil;
import com.seek.food.util.Redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;

@Service
@RefreshScope
@Slf4j
public class MerchantServiceImpl implements MerchantService {
    private final MerchantMapper merchantMapper;
    private final MerchantRedisKeyConfig merchantRedisKeyConfig;
    private final MerchantParamsRulesConfig merchantParamsRulesConfig;
    private final MerchantRedisStreamConfig merchantRedisStreamConfig;
    private final MerchantEsTableConfig merchantEsTableConfig;
    private final CommonParamRulesConfig commonParamRulesConfig;
    private final MerchantCaffeine merchantCaffeine;
    private final StringRedisTemplate stringRedisTemplate;
    private final ElasticsearchClient esClient;
    private final MerchantExchangeConfig merchantExchangeConfig;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MerchantServiceImpl(MerchantMapper merchantMapper, MerchantRedisKeyConfig merchantRedisKeyConfig, MerchantRedisStreamConfig merchantRedisStreamConfig
    , MerchantEsTableConfig merchantEsTableConfig, CommonParamRulesConfig commonParamRulesConfig, MerchantCaffeine merchantCaffeine
    , StringRedisTemplate stringRedisTemplate, ElasticsearchClient esClient, MerchantParamsRulesConfig merchantParamsRulesConfig, MerchantExchangeConfig merchantExchangeConfig
    ,RabbitTemplate rabbitTemplate) {
        this.merchantMapper = merchantMapper;
        this.merchantRedisKeyConfig = merchantRedisKeyConfig;
        this.merchantParamsRulesConfig = merchantParamsRulesConfig;
        this.merchantRedisStreamConfig = merchantRedisStreamConfig;
        this.merchantEsTableConfig = merchantEsTableConfig;
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.merchantCaffeine = merchantCaffeine;
        this.stringRedisTemplate = stringRedisTemplate;
        this.esClient = esClient;
        this.rabbitTemplate = rabbitTemplate;
        //提前创建目录
        FileSave.createDestDir(merchantParamsRulesConfig.getMasterImageDest());
        FileSave.createDestDir(merchantParamsRulesConfig.getProofImageDest());
        FileSave.createDestDir(merchantParamsRulesConfig.getShowImageDest());
        FileSave.createDestDir(merchantParamsRulesConfig.getHomeImageDest());
        this.merchantExchangeConfig = merchantExchangeConfig;
    }


    @Override
    public MerchantDTO getMerchantDetail(long merchantId){
        commonParamRulesConfig.merchantIdCheck(merchantId);
        return merchantCaffeine.getAndAutoLoad(merchantId,stringRedisTemplate,merchantRedisKeyConfig.getMerchantMessageCaffeine().getName()+merchantId,
                merchantRedisKeyConfig.getMerchantMessageCaffeine().getDuration(),MerchantDTO.class,k->merchantMapper.getMerchantById(merchantId));
    }


    @Override
    public MerchantDTO getMerchantSelf(){
        long merchantId= TokenIdContext.getAndCheck(commonParamRulesConfig.getMerchantIdStart(),commonParamRulesConfig.getIdCapacity());
        return getMerchantDetail(merchantId);
    }

    //设置店主信息
    @Override
    public void setMerchantMaster(String masterName, String masterCode
            , @RequestBody MultipartFile masterImage){
        //获取商家id
        long merchantId=TokenIdContext.getAndCheck(commonParamRulesConfig.getMerchantIdStart(),commonParamRulesConfig.getIdCapacity());
        //检查是否还未设置过该信息
        if (RedisUtil.oftenGetBit(stringRedisTemplate,merchantRedisKeyConfig.getMerchantMasterIsSet().getName(),merchantId,
                commonParamRulesConfig.getIdCapacity()))throw new BizException(ErrorCodeEnum.CONDITION_NOT_PASS);
        //检查格式
        commonParamRulesConfig.personNameCheck(masterName);
        commonParamRulesConfig.codeCheck(masterCode);
        //检查冷却期，同时作为分布式锁防止多重设置
        RedisUtil.checkCooldown(stringRedisTemplate,merchantRedisKeyConfig.getMerchantUpdateMasterCooldown().getName()+merchantId,
                merchantRedisKeyConfig.getMerchantUpdateMasterCooldown().getDuration());
        //检查照片并且保存
        String addr=FileSave.quickCheckAndSaveFile(masterImage,merchantParamsRulesConfig.getMasterImageDest()
                ,commonParamRulesConfig.getImageSize(),commonParamRulesConfig.getImageType());
        //写入mysql
        merchantCaffeine.updateAndRemoveCaffeine(merchantId,stringRedisTemplate,merchantRedisKeyConfig.getMerchantMessageCaffeine().getName()+merchantId
                , k->merchantMapper.setMerchantMaster(merchantId,masterName,masterCode,addr));
        //设置该信息已经被修改
        RedisUtil.oftenSetBit(stringRedisTemplate,merchantRedisKeyConfig.getMerchantMasterIsSet().getName(),merchantId
                ,true,commonParamRulesConfig.getIdCapacity());
    }

    //添加营业证明照片
    @Override
    @Transactional
    public void addMerchantProofImage(MultipartFile image){
        //获取id
        long merchantId=TokenIdContext.getAndCheck(commonParamRulesConfig.getMerchantIdStart(),commonParamRulesConfig.getIdCapacity());
        //检查冷却
        RedisUtil.checkCooldown(stringRedisTemplate, merchantRedisKeyConfig.getMerchantAddProofCooldown().getRedisKey(merchantId)
                ,merchantRedisKeyConfig.getMerchantAddProofCooldown().getDuration());
        //检查文件，并且保存
        String addr=FileSave.quickCheckAndSaveFile(image,merchantParamsRulesConfig.getProofImageDest(),commonParamRulesConfig.getImageSize(),commonParamRulesConfig.getImageType());
        //写入MySQL，并检查是否成功
        if (!merchantMapper.addMerchantProofImage(merchantId,addr,merchantParamsRulesConfig.getProofImageNumberMax())) {
            //发消息使已经保存文件删除
            quickToMQDeleteFile(merchantParamsRulesConfig.getProofImageDest(),addr);
            throw new BizException(ErrorCodeEnum.CONDITION_NOT_PASS);
        }
    }

    //删除营业证明照片
    @Override
    public void removeMerchantProofImage(int index){
        long merchantId=TokenIdContext.getAndCheck(commonParamRulesConfig.getMerchantIdStart(),commonParamRulesConfig.getIdCapacity());
        //检查冷却
        RedisUtil.checkCooldown(stringRedisTemplate, merchantRedisKeyConfig.getMerchantRemoveProofCooldown().getName()+merchantId,merchantRedisKeyConfig.getMerchantAddProofCooldown().getDuration());
        //回显获取信息
        String oldAddr=quickGetProofOldAddr(merchantId,index);
        //mysql删除该地址,防止可能出现的并发问题，虽然加了冷却期，但是仍旧提防
        if(!merchantMapper.removeMerchantProofImage(merchantId,oldAddr,index))throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        //发消息使已经保存文件删除
        quickToMQDeleteFile(merchantParamsRulesConfig.getProofImageDest(),oldAddr);
    }

    //更换营业证明照片
    @Override
    public void replaceMerchantProofImage(MultipartFile image,int index){
        long merchantId=TokenIdContext.getAndCheck(commonParamRulesConfig.getMerchantIdStart(),commonParamRulesConfig.getIdCapacity());
        //检查冷却
        RedisUtil.checkCooldown(stringRedisTemplate, merchantRedisKeyConfig.getMerchantReplaceProofCooldown().getRedisKey(merchantId)
                ,merchantRedisKeyConfig.getMerchantReplaceShowCooldown().getDuration());
        //回显获取信息
        String oldAddr=quickGetProofOldAddr(merchantId,index);
        //检查文件，并且保存
        String addr=FileSave.quickCheckAndSaveFile(image,merchantParamsRulesConfig.getProofImageDest(),commonParamRulesConfig.getImageSize(),commonParamRulesConfig.getImageType());
        //mysql更新该地址,防止可能出现的并发问题，虽然加了冷却期，但是仍旧提防
        if(!merchantMapper.replaceMerchantProofImage(merchantId,addr,oldAddr,index))throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        //发消息使已经保存文件删除
        quickToMQDeleteFile(merchantParamsRulesConfig.getProofImageDest(),oldAddr);
    }

    //添加商家展示照片
    @Override
    public void addShowImage(MultipartFile image){
        //获取id
        long merchantId=TokenIdContext.getAndCheck(commonParamRulesConfig.getMerchantIdStart(),commonParamRulesConfig.getIdCapacity());
        //检查冷却
        RedisUtil.checkCooldown(stringRedisTemplate, merchantRedisKeyConfig.getMerchantAddShowCooldown().getRedisKey(merchantId)
                ,merchantRedisKeyConfig.getMerchantAddShowCooldown().getDuration());
        //检查文件，并且保存
        String addr=FileSave.quickCheckAndSaveFile(image,merchantParamsRulesConfig.getShowImageDest()
                ,commonParamRulesConfig.getImageSize(),commonParamRulesConfig.getImageType());
        //写入MySQL，并检查是否成功
        if (!merchantMapper.addShowImage(merchantId,addr,merchantParamsRulesConfig.getShowImageNumberMax())) {
            //发消息使已经保存文件删除
            quickToMQDeleteFile(merchantParamsRulesConfig.getShowImageDest(),addr);
            throw new BizException(ErrorCodeEnum.CONDITION_NOT_PASS);
        }
    }

    //删除商家展示照片
    @Override
    public void removeShowImage(int index){
        long merchantId=TokenIdContext.getAndCheck(commonParamRulesConfig.getMerchantIdStart(),commonParamRulesConfig.getIdCapacity());
        //检查冷却
        RedisUtil.checkCooldown(stringRedisTemplate, merchantRedisKeyConfig.getMerchantRemoveShowCooldown().getRedisKey(merchantId)
                ,merchantRedisKeyConfig.getMerchantRemoveShowCooldown().getDuration());
        //回显获取信息
        String oldAddr=quickGetShowOldAddr(merchantId,index);
        //mysql删除该地址,防止可能出现的并发问题，虽然加了冷却期，但是仍旧提防
        if(!merchantMapper.removeShowImage(merchantId,oldAddr,index))throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        //发消息使已经保存文件删除
        quickToMQDeleteFile(merchantParamsRulesConfig.getShowImageDest(),oldAddr);
    }

    //更新商家展示照片
    @Override
    public void replaceShowImage(MultipartFile image,int index){
        long merchantId=TokenIdContext.getAndCheck(commonParamRulesConfig.getMerchantIdStart(),commonParamRulesConfig.getIdCapacity());
        //检查冷却
        RedisUtil.checkCooldown(stringRedisTemplate, merchantRedisKeyConfig.getMerchantReplaceShowCooldown().getRedisKey(merchantId)
                ,merchantRedisKeyConfig.getMerchantReplaceShowCooldown().getDuration());
        //回显获取信息
        String oldAddr=quickGetShowOldAddr(merchantId,index);
        //检查文件，并且保存
        String addr=FileSave.quickCheckAndSaveFile(image,merchantParamsRulesConfig.getShowImageDest()
                ,commonParamRulesConfig.getImageSize(),commonParamRulesConfig.getImageType());
        //mysql更新该地址,防止可能出现的并发问题，虽然加了冷却期，但是仍旧提防
        if(!merchantMapper.replaceShowImage(merchantId,addr,oldAddr,index))throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        //发消息使已经保存文件删除
        quickToMQDeleteFile(merchantParamsRulesConfig.getShowImageDest(),oldAddr);
    }

    //更新商家的封面图片
    @Override
    public void updateHomeImage(MultipartFile image){
        //获取id
        long merchantId=TokenIdContext.getAndCheck(commonParamRulesConfig.getMerchantIdStart(),commonParamRulesConfig.getIdCapacity());
        //冷却期校验
        RedisUtil.checkCooldown(stringRedisTemplate,merchantRedisKeyConfig.getMerchantUpdateHomeCooldown().getRedisKey(merchantId)
                ,merchantRedisKeyConfig.getMerchantUpdateHomeCooldown().getDuration());
        //获取旧头像路径
        String oldAddr=merchantMapper.getHomeImage(merchantId);
        //快速保存
        String addr=FileSave.quickCheckAndSaveFile(image,merchantParamsRulesConfig.getHomeImageDest(), commonParamRulesConfig.getImageSize()
                , commonParamRulesConfig.getImageType());
        //检查是否成功
        if (!merchantMapper.updateHomeImage(merchantId,addr,oldAddr)) {
            //发消息使已经保存文件删除
            quickToMQDeleteFile(merchantParamsRulesConfig.getHomeImageDest(),addr);
            throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        }
        //如果成功,且旧地址不为空,发送消息到mq中删除旧文件
        if (oldAddr!=null&&!oldAddr.isEmpty()) quickToMQDeleteFile(merchantParamsRulesConfig.getHomeImageDest(),oldAddr);
    }

    //快捷的发送至MQ删除文件
    private void quickToMQDeleteFile(String dest,String addr){
        //发消息使已经保存文件删除
        MQUtil.send(merchantExchangeConfig.getExchangeName(),merchantExchangeConfig.getDeleteFileMerchantQueue().getRoutingKey()
                , Paths.get(dest,addr).toString(),rabbitTemplate);
    }
    //快速获取证明图片旧地址
    private String quickGetProofOldAddr(long merchantId,int index){
        String oldAddr=merchantMapper.getMerchantProofImageByIndex(merchantId,index);
        if (oldAddr==null)throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        return oldAddr;
    }
    //快速获取展示图片旧地址
    private String quickGetShowOldAddr(long merchantId,int index){
        String oldAddr=merchantMapper.getShowImageByIndex(merchantId,index);
        if (oldAddr==null)throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
        return oldAddr;
    }





}
