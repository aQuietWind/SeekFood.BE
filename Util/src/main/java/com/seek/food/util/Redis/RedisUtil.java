package com.seek.food.util.Redis;

import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import com.seek.food.util.TimeUtil.DurationUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.List;

public class RedisUtil {
    public static final String cooldownValue="true";
    public static DefaultRedisScript<Boolean> luaQuickInit(String path){
        //初始化脚本对象
        DefaultRedisScript<Boolean> luaScript= new DefaultRedisScript<>();
        luaScript.setLocation(new ClassPathResource(path));  //设置Lua脚本地址，一般放于resources/Lua下
        luaScript.setResultType(Boolean.class);      //设置脚本返回值，与泛型保持一致
        return luaScript;
    };
    //用于满足lua脚本的集合化key操作
    public static List<String> toCollect(String ... items){
        return Arrays.asList(items);
    }

    //快速鉴别是否处于冷却期
    public static void checkCooldown(StringRedisTemplate stringRedisTemplate,String key,long duration){
        if (!Boolean.TRUE.equals(stringRedisTemplate.opsForValue().setIfAbsent(key, cooldownValue, DurationUtil.getSecondDuration(duration)))) throw new BizException(ErrorCodeEnum.REQUEST_IN_COOLDOWN);
    }
}
