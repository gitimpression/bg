package com.bg.interceptor;

import com.bg.config.KeysProperties;
import com.bg.entity.User;
import com.bg.service.RolePermissionService;
import com.bg.service.UserService;
import com.bg.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ctp
 * @date 2022/12/5 16:10
 */
@Component
public class SystemMenuInterceptor implements HandlerInterceptor {
    @Resource
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!"GET".equals(request.getMethod())){
            String token = request.getHeader(KeysProperties.TOKEN_KEY);
            long userId = Long.parseLong(JwtUtil.claims(token, KeysProperties.TOKEN_USER_ID_KEY));
            User user = userService.getUserById(userId);
            return user.getRoleId() == 3 && user.getRoleId() == 4;
        }
        return true;
    }
}
