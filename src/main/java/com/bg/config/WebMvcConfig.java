package com.bg.config;

import com.bg.interceptor.NoticeOperationInterceptor;
import com.bg.interceptor.UserLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
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
                        "/**/*.PNG", "/**/*.JPG", "/**/*.JPEG");    // 放行图片资源请求
        // 公告操作拦截
        registry.addInterceptor(noticeOperationInterceptor)
                .addPathPatterns("/notice","/notice/batch");
    }
}
