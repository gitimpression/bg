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
 * 网络服务 操作权限检查
 * @author ctp
 * @date 2022/12/4 12:02
 */
@Component
public class NetHandbookOperationInterceptor implements HandlerInterceptor {
    @Resource
    private RolePermissionService rolePermissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!"GET".equals(request.getMethod())) {
            String token = request.getHeader(KeysProperties.TOKEN_KEY);
            long userId = Long.parseLong(JwtUtil.claims(token, KeysProperties.TOKEN_USER_ID_KEY));
            Integer id = rolePermissionService.getRolePermissionId(userId, KeysProperties.USER_NET_PERMISSION_KEY);
            return id != null;
        }
        return true;
    }
}
