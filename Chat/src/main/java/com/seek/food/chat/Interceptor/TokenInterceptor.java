package com.seek.food.chat.Interceptor;

import com.seek.food.util.Context.TokenIdContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handle)throws Exception{
        System.out.println(1);
        // 获取TokenId
        String tokenId  = request.getHeader("X-Token-Id");
        System.out.println("tokenId:"+tokenId);
        if (tokenId!=null)TokenIdContext.set(tokenId);
        System.out.println(TokenIdContext.get());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handle, Exception ex)throws Exception{
        TokenIdContext.remove();
    }
}
