package com.seek.food.user.Config.CommonConfig;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.UrlCleaner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//进行接口汇总统一限流,生产环境更推荐根据接口路径精细化控制
@Configuration
public class SentinelGlobalApiConfig {
    @Value("${common.self.all-service-api-name}")
    private String allServiceApiName;

    @Bean
    public UrlCleaner allApiUrlCleaner() {
        return originUrl -> {
            // 匹配你服务所有业务接口前缀，统一汇总到全局资源 all_user_service_api
            if (originUrl.startsWith("/")) {
                return allServiceApiName;
            }
            // 静态资源/特殊路径保留独立资源
            return originUrl;
        };
    }
}
