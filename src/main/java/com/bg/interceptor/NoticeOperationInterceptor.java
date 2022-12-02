package com.bg.interceptor;

import com.bg.config.KeysProperties;
import com.bg.service.RolePermissionService;
import com.bg.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 操作日志权限
 * @author ctp
 * @date 2022/12/3 4:09
 */
@Component
@Slf4j
public class NoticeOperationInterceptor implements HandlerInterceptor {
    @Resource
    private RolePermissionService rolePermissionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!"GET".equals(request.getMethod())) {
            String token = request.getHeader(KeysProperties.TOKEN_KEY);
            if (StringUtils.isEmpty(token)) {
                return false;
            }
            long userId = Long.parseLong(JwtUtil.claims(token, KeysProperties.TOKEN_USER_ID_KEY));
            // 权限校验
            Integer permissionId = rolePermissionService.getRolePermissionId(userId, KeysProperties.USER_NOTICE_PERMISSION_KEY);
            return permissionId != null;
        }
        return true;
    }
}
