package com.seek.food.user.Config.CommonConfig;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

//请求头名单判别，用于sentinel的请求过滤
@Component
public class HeaderOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest var1){
        //固定默认鉴定,直接硬编码即可
        String origin = var1.getHeader("origin");
        if (origin == null|| origin.isEmpty()) return "blank";   //返回blank
        return origin;      //重新返回该请求头标识
    }
}