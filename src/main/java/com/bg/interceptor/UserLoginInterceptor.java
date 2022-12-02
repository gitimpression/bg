package com.bg.interceptor;

import com.bg.config.KeysProperties;
import com.bg.handler.CustomException;
import com.bg.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截掉没有登录的用户
 * 拦截掉没有 token、token过时 或 token错误的 的请求
 * @author ctp
 * @date 2022/11/27 22:09
 */
@Component
public class UserLoginInterceptor implements HandlerInterceptor {

    /**
     * 请求前拦截
     * @param request request
     * @param response response
     * @param handler handler
     * @return return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        String token = request.getHeader(KeysProperties.TOKEN_KEY);
        boolean flag = !StringUtils.isEmpty(token) && JwtUtil.parse(token) != null;
        if (flag){
            return true;
        }else{
            throw new CustomException("用户未登录或者登录状态已过期，请重新登录");
        }
    }
}
