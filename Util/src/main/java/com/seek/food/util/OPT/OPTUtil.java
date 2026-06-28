package com.seek.food.util.OPT;

import com.seek.food.util.Exception.BizException;
import com.seek.food.util.Exception.ErrorCodeEnum;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Random;

public class OPTUtil {
    public static String generateOPT(int n){
        StringBuilder opt = new StringBuilder();
        for (int i = 0; i < n; i++) {
            //添加一位随机数字
            opt.append(new Random().nextInt(10));
        }
        return opt.toString();
    }
    public static void checkOPT(StringRedisTemplate stringRedisTemplate, String keyName, String opt){
        //获取redis的验证码
        String originOpt=stringRedisTemplate.opsForValue().get(keyName);
        //检验验证码
        if ( originOpt== null) throw new BizException(ErrorCodeEnum.OPT_NOT_SURVIVE);
        if (!originOpt.equals(opt)) throw new BizException(ErrorCodeEnum.OPT_NOT_SAME);
        stringRedisTemplate.delete(keyName);
    }
}
