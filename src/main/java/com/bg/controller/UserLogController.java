package com.bg.controller;

import com.bg.anno.Log;
import com.bg.config.KeysProperties;
import com.bg.entity.SimpleUserLog;
import com.bg.entity.UserLog;
import com.bg.service.UserLogService;
import com.bg.util.ComRet;
import com.bg.util.JwtUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.Map;

/**
 * 访问用户日志
 *
 * @author ctp
 * @date 2022/11/30 13:34
 */
@RestController
@RequestMapping("/userLog")
public class UserLogController {
    @Resource
    private UserLogService userLogService;

    @Log("查询用户的分页日志记录")
    @GetMapping("/page")
    public ComRet getAllUserLogPage(@RequestHeader Map<String, String> headers,
                                 Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageSize == null)
            return ComRet.fail("缺少参数");
        long userId = Long.parseLong(JwtUtil.claims(headers, KeysProperties.TOKEN_USER_ID_KEY));
        PageInfo<UserLog> pageInfo =
                userLogService.getAllUserLogPage(userId, pageNum, pageSize);
        return ComRet.ok().add("pageInfo", pageInfo);
    }

    @Log("查询指定用户的日志记录")
    @GetMapping("/user")
    public ComRet getUserLogPage(@RequestHeader Map<String, String> headers,
                                        Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageSize == null)
            return ComRet.fail("缺少参数");
        long userId = Long.parseLong(JwtUtil.claims(headers, KeysProperties.TOKEN_USER_ID_KEY));
        PageInfo<UserLog> pageInfo =
                userLogService.getUserLogPage(userId, pageNum, pageSize);
        return ComRet.ok().add("pageInfo", pageInfo);
    }

    @Log("用户查询自己的简单日志记录")
    @GetMapping("/simple")
    public ComRet getSimpleUserLog(@RequestHeader Map<String, String> headers,
                                   Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageSize == null)
            return ComRet.fail("缺少参数");
        long userId = Long.parseLong(JwtUtil.claims(headers, KeysProperties.TOKEN_USER_ID_KEY));
        PageInfo<SimpleUserLog> pageInfo =
                userLogService.getSimpleUserLog(userId, pageNum, pageSize);
        return ComRet.ok().add("pageInfo", pageInfo);
    }

    @Log("用户查询自己的登录日志记录")
    @GetMapping("/login")
    public ComRet getSimpleUserLoginLog(@RequestHeader Map<String, String> headers,
                                        Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageSize == null)
            return ComRet.fail("缺少参数");
        long userId = Long.parseLong(JwtUtil.claims(headers, KeysProperties.TOKEN_USER_ID_KEY));
        PageInfo<SimpleUserLog> pageInfo =
                userLogService.getSimpleUserLoginLog(userId, pageNum, pageSize);
        return ComRet.ok().add("pageInfo", pageInfo);
    }

}
