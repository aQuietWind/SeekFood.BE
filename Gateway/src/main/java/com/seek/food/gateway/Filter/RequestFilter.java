package com.seek.food.gateway.Filter;


import com.github.benmanes.caffeine.cache.Cache;
import com.seek.food.gateway.Config.GatewayConfig;
import com.seek.food.gateway.Config.RedisKeyConfig;
import com.seek.food.gateway.Util.BlackIdCaffeine;
import com.seek.food.gateway.Util.BlackIpCaffeine;
import com.seek.food.util.CommonUtil.LocalDateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Order(2)
//@Component
@RefreshScope
public class RequestFilter implements GlobalFilter, ApplicationContextAware {

    //构造器注入
    private StringRedisTemplate stringRedisTemplate;
    private RedisKeyConfig redisKeyConfig;
    private BlackIpCaffeine blackIpCaffeine;
    private BlackIdCaffeine blackIdCaffeine;
    private GatewayConfig gatewayConfig;
    private int blackIpTimes;
    private int blackIpDuration;
    private int blackIdTimes;
    private int blackIdDuration;
    private static final Logger logger = LoggerFactory.getLogger(RequestFilter.class);
    // 容器初始化好后，手动获取Bean
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.stringRedisTemplate = applicationContext.getBean(StringRedisTemplate.class);
        this.redisKeyConfig = applicationContext.getBean(RedisKeyConfig.class);
        this.blackIpCaffeine = applicationContext.getBean(BlackIpCaffeine.class);
        this.blackIdCaffeine = applicationContext.getBean(BlackIdCaffeine.class);
        this.gatewayConfig = applicationContext.getBean(GatewayConfig.class);
        this.blackIpTimes=gatewayConfig.getBlackIpCounts();
        this.blackIdTimes=gatewayConfig.getBlackIdCounts();
        this.blackIpDuration=gatewayConfig.getBlackIpDuration();
        this.blackIdDuration=gatewayConfig.getBlackIdDuration();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
        logger.info("请求进入RequestFilter");
        ServerHttpRequest request = exchange.getRequest();
        //获取tokenId
        List<String> tokens = request.getHeaders().get("X-Token-Id");
        String tokenId=tokens==null?null:tokens.getFirst();
        //检验是不是去公开路径的,并检查ip是否处于黑名单
        if (("".equals(tokenId)||tokenId==null)&&ipCheck(request))return chain.filter(exchange);
        else if (tokenId!=null&&idRecord(tokenId))return chain.filter(exchange);
        exchange.getResponse().setStatusCode(HttpStatus.BAD_GATEWAY);      //设置状态码
        return exchange.getResponse().setComplete();        //拒绝请求
    }

    //ip名单校验
    private boolean ipCheck(ServerHttpRequest request){
        //获取ip
        String ip=request.getHeaders().getFirst("X-Forwarded-For");
        if(ip==null||ip.equals("unknown")||ip.isBlank()){
            return false;
        }
        //进行ip检查
        return recordCount(
                blackIpCaffeine.getCACHE(),
                redisKeyConfig.getIpBlackKey(),
                ip,
                redisKeyConfig.getIpCheckKey(),
                blackIpTimes,
                blackIpDuration
                );      //检查名单
    }

    //id记录
    private boolean idRecord(String id){
        return recordCount(
                blackIdCaffeine.getCACHE(),
                redisKeyConfig.getIdBlackKey(),
                id,
                redisKeyConfig.getIdCheckKey(),
                blackIdTimes,
                blackIdDuration
        );
    }

    //检查流程
    private boolean recordCount(Cache<String,Long> cache,String redisBlackKey,String value,String redisRecordKey,int maxCounts,int blackDuration){
        //检查caffeine和redis中是否存在黑名单
        if (cache.get(value,key-> {
                String redisCheckResult =stringRedisTemplate.opsForValue().get(redisBlackKey+value);
                return redisCheckResult==null?null:Long.parseLong(redisCheckResult);})!=null)return false;
        //自增来访次数并检查
        long count=stringRedisTemplate.opsForValue().increment(redisRecordKey+value);
        if (count>= maxCounts){
            //持续时间戳
            long aimStamp=LocalDateTimeUtil.getPlusHoursStampByNow(blackDuration);
            //封禁并且设置有效期
            stringRedisTemplate.opsForValue().set(redisBlackKey+value, ""+aimStamp,blackDuration, TimeUnit.HOURS);
            //回写jvm缓存
            cache.put(value,aimStamp);
            //拒绝放行
            return false;
        }
        //第一次来访设置1分钟有效期
        if (count==1)stringRedisTemplate.expire(redisRecordKey+value,1,TimeUnit.MINUTES);
        logger.info("请求黑名单验证成功");
        //同意放行
        return true;
    }













}
