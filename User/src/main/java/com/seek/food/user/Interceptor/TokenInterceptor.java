package com.seek.food.user.Interceptor;

import com.seek.food.config.NacosConfig.Common.JWTConfig;
import com.seek.food.util.Context.TokenIdContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
    //构造器注入
    private final JWTConfig jwtConfig;
    @Autowired
    public TokenInterceptor(JWTConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handle)throws Exception{
        // 获取TokenId
        String tokenId  = request.getHeader(jwtConfig.getHeaderTokenName());
        logger.info("tokenId:{} ,进入user模块", tokenId);
        //放入context上下文
        if (tokenId!=null)TokenIdContext.set(tokenId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handle, Exception ex)throws Exception{
        TokenIdContext.remove();
    }
}
