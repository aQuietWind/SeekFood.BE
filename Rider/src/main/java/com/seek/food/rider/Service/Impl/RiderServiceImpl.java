package com.seek.food.rider.Service.Impl;

import com.seek.food.config.NacosConfig.Common.CommonParamRulesConfig;
import com.seek.food.config.NacosConfig.Rider.RiderRedisKeyConfig;
import com.seek.food.dto.Rider.RiderDTO;
import com.seek.food.rider.Consumer.RiderCaffeine;
import com.seek.food.rider.Consumer.RiderPhoneCaffeine;
import com.seek.food.rider.Mapper.RiderMapper;
import com.seek.food.rider.Service.RiderService;
import com.seek.food.util.Context.TokenIdContext;
import com.seek.food.util.OPT.OPTUtil;
import com.seek.food.util.Redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RefreshScope
public class RiderServiceImpl implements RiderService {


    private final CommonParamRulesConfig commonParamRulesConfig;
    private final RiderCaffeine riderCaffeine;
    private final StringRedisTemplate stringRedisTemplate;
    private final RiderRedisKeyConfig riderRedisKeyConfig;
    private final RiderMapper riderMapper;
    private final RiderPhoneCaffeine riderPhoneCaffeine;

    public RiderServiceImpl(CommonParamRulesConfig commonParamRulesConfig, RiderCaffeine riderCaffeine, StringRedisTemplate stringRedisTemplate, RiderRedisKeyConfig riderRedisKeyConfig, RiderMapper riderMapper, RiderPhoneCaffeine riderPhoneCaffeine) {
        this.commonParamRulesConfig = commonParamRulesConfig;
        this.riderCaffeine = riderCaffeine;
        this.stringRedisTemplate = stringRedisTemplate;
        this.riderRedisKeyConfig = riderRedisKeyConfig;
        this.riderMapper = riderMapper;
        this.riderPhoneCaffeine = riderPhoneCaffeine;
    }

    public RiderDTO getDetail(long riderId){
        //检查id
        commonParamRulesConfig.riderIdCheck(riderId);
        //获取骑手信息
        return riderCaffeine.getAndAutoLoad(riderId,stringRedisTemplate,riderRedisKeyConfig.getRiderMessageCaffeine().getRedisKey(riderId)
                ,riderRedisKeyConfig.getRiderMessageCaffeine().getDuration(),RiderDTO.class,k->riderMapper.getDetail(riderId));
    }

    public RiderDTO getSelf(){
        return getDetail(quickGetRiderId());
    }

    public void updatePersonImage(MultipartFile file){
        
    }

    public String getUpdatePasswordOpt(){
        //获取id
        long riderId = quickGetRiderId();
        //获取手机号
        String phone=quickGetPhone(riderId);
        //生成验证码
        return OPTUtil.generateOPTAndRecord(stringRedisTemplate,riderRedisKeyConfig.getUpdatePasswordOpt().getRedisKey(phone)
                ,riderRedisKeyConfig.getUpdatePasswordOpt().getDuration(),6);
    }

    public void updatePassword(String password, String opt){
        //获取id
        long riderId = quickGetRiderId();
        //获取手机号
        String phone=quickGetPhone(riderId);
        //检查验证码
        OPTUtil.checkOPT(stringRedisTemplate,riderRedisKeyConfig.getUpdatePasswordOpt().getRedisKey(phone),opt);
        //检查冷却期
        RedisUtil.checkCooldown(stringRedisTemplate,riderRedisKeyConfig.getUpdatePasswordCooldown().getRedisKey(phone)
                , riderRedisKeyConfig.getUpdatePasswordCooldown().getDuration());
        //更新密码
        riderMapper.updatePassword(password,riderId);
    }

    public String getDeleteOpt(){
        //获取id
        long riderId = quickGetRiderId();
        //获取手机号
        String phone=quickGetPhone(riderId);
        //生成验证码
        return OPTUtil.generateOPTAndRecord(stringRedisTemplate,riderRedisKeyConfig.getDeleteRiderOpt().getRedisKey(phone)
                ,riderRedisKeyConfig.getDeleteRiderOpt().getDuration(),6);
    }

    public void delete(String opt){
        //获取id
        long riderId = quickGetRiderId();
        //获取手机号
        String phone=quickGetPhone(riderId);
        //检查验证码
        OPTUtil.checkOPT(stringRedisTemplate,riderRedisKeyConfig.getDeleteRiderOpt().getRedisKey(phone),opt);
        //删除骑手
        riderMapper.delete(riderId);
    }

    private long quickGetRiderId(){
        return TokenIdContext.getAndCheck(commonParamRulesConfig.getRiderIdStart(),commonParamRulesConfig.getIdCapacity());
    }

    private String quickGetPhone(long riderId){
        return riderPhoneCaffeine.getAndAutoLoad(riderId,stringRedisTemplate,riderRedisKeyConfig.getRiderPhoneCaffeine().getRedisKey(riderId)
                ,riderRedisKeyConfig.getRiderPhoneCaffeine().getDuration(),String.class,k->riderMapper.getPhoneByRiderId(riderId));
    }
}
