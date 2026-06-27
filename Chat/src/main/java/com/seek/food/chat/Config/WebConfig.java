package com.seek.food.chat.Config;

import com.seek.food.chat.Interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration          //声明这是一个对项目进行配置的配置类
public class WebConfig implements WebMvcConfigurer {            //实现
    @Autowired          //依赖注入
    private TokenInterceptor tokenInterceptor;          //获取拦截器的对象
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/**");        //添加拦截器，且声明该拦截器将拦截所有请求，"/*"只能匹配一级路径，不能匹配"/xx1/xx2/..."
//                .excludePathPatterns("");         //不拦截指定路径，该参数可为集合

    }
}
