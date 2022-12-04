package com.bg.interceptor;

import com.bg.config.KeysProperties;
import com.bg.service.RolePermissionService;
import com.bg.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ctp
 * @date 2022/12/4 13:33
 */
@Component
public class FeedbackInterceptor implements HandlerInterceptor {
    @Resource
    private RolePermissionService rolePermissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!"PUT".equals(request.getMethod())) {
            String token = request.getHeader(KeysProperties.TOKEN_KEY);
            long userId = Long.parseLong(JwtUtil.claims(token, KeysProperties.TOKEN_USER_ID_KEY));
            Integer id = rolePermissionService.getRolePermissionId(userId, KeysProperties.USER_FEEDBACK_OPERATION_PERMISSION_KEY);
            return id != null;
        }
        return true;
    }
}
