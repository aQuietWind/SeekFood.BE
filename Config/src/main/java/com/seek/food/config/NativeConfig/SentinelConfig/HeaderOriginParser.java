package com.seek.food.config.NativeConfig.SentinelConfig;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;

// 仅普通Servlet微服务生效，WebFlux网关不会实例化这个类
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
//请求头名单判别，用于Service模块对于sentinel的请求过滤
@Component
public class HeaderOriginParser implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest var1){
        String origin = var1.getHeader("origin");
        if (origin == null|| origin.isEmpty()) return "blank";   //返回blank
        return origin;      //重新返回该请求头标识
    }
}