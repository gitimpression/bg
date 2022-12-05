package com.bg.config;

import com.bg.interceptor.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 配置类
 *
 * @author ctp
 * @date 2022/11/27 22:11
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private NoticeOperationInterceptor noticeOperationInterceptor;
    @Resource
    private UserLoginInterceptor userLoginInterceptor;
    @Resource
    private NetHandbookOperationInterceptor netOperationInterceptor;
    @Resource
    private FeedbackInterceptor feedbackInterceptor;
    @Resource
    private PermissionInterceptor permissionInterceptor;
    @Resource
    private SystemMenuInterceptor systemMenuInterceptor;

    /**
     * 配置拦截器
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截欸有登录的用户的请求
        registry.addInterceptor(userLoginInterceptor)
                .addPathPatterns("/**")              // 拦截所有请求
                .excludePathPatterns("/verifyCodeImg")  // 放行验证码请求
                .excludePathPatterns("/user/login")     // 放行登录请求
                .excludePathPatterns("/**/*.png", "/**/*.jpg", "/**/*.jpeg",
                        "/**/*.PNG", "/**/*.JPG", "/**/*.JPEG")    // 放行图片资源请求
                .excludePathPatterns("/user/expire");  // 校验登录是否过期
        // 公告操作拦截
        registry.addInterceptor(noticeOperationInterceptor)
                .addPathPatterns("/notice", "/notice/batch");
        // 网络服务 手册 拦截
        registry.addInterceptor(netOperationInterceptor)
                .addPathPatterns("/netHandbook");
        // 反馈 拦截
        registry.addInterceptor(feedbackInterceptor)
                .addPathPatterns("/feedback");
        // 系统管理接口
        registry.addInterceptor(systemMenuInterceptor)
                .addPathPatterns("/sys");
        // 权限管理接口
        registry.addInterceptor(permissionInterceptor)
                .addPathPatterns("/pm");
    }
}
