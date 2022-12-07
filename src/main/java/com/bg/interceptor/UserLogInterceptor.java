package com.bg.interceptor;

import com.bg.config.KeysProperties;
import com.bg.entity.User;
import com.bg.service.UserService;
import com.bg.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ctp
 * @date 2022/12/7 11:11
 */
@Component
public class UserLogInterceptor implements HandlerInterceptor {
    @Resource
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 查询详尽的用户日志需要管理员权限
        String token = request.getHeader(KeysProperties.TOKEN_KEY);
        long userId = Long.parseLong(JwtUtil.claims(token, KeysProperties.TOKEN_USER_ID_KEY));
        User user = userService.getUserById(userId);
        return user.getRoleId() == 3 || user.getRoleId() == 4;
    }
}
