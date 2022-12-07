package com.bg.controller;

import com.bg.anno.Log;
import com.bg.config.KeysProperties;
import com.bg.entity.SimpleUserLog;
import com.bg.entity.UserLog;
import com.bg.service.UserLogService;
import com.bg.service.UserService;
import com.bg.util.ComRet;
import com.bg.util.JwtUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    public ComRet getAllUserLogPage(@RequestParam Map<String, String> params) {
        Integer pageNum = null;
        Integer pageSize = null;

        pageNum = StringUtils.isEmpty(params.get("pageNum")) ? 1 : Integer.parseInt(params.get("pageNum"));
        pageSize = StringUtils.isEmpty(params.get("pageSize")) ? 20 : Integer.parseInt(params.get("pageSize"));

        UserLog userLog = new UserLog();
        Long userId = StringUtils.isEmpty(params.get("userId")) ? null : Long.parseLong(params.get("userId"));
        if (userId != null){
            userLog.setUserId(userId);
        }

        if (!StringUtils.isEmpty(params.get("userId"))) {
            userLog.setUserId(Long.parseLong(params.get("userId")));
        }
        if (!StringUtils.isEmpty(params.get("method"))) {
            if (KeysProperties.USER_LOG_METHOD_FILTER_ARRAY.contains(params.get("method"))) {
                // TODO 更好的搜索 当前只是模糊搜索
                userLog.setMethod(params.get("method"));
            }
        }
        String createBeginTime = null;
        String createEndTime = null;
        // 创建时间区间
        if (!StringUtils.isEmpty(params.get("createBeginTime")) || !StringUtils.isEmpty(params.get("createEndTime"))) {
            createBeginTime =
                    StringUtils.isEmpty(params.get("createBeginTime")) ? null : params.get("createBeginTime");
            createEndTime =
                    StringUtils.isEmpty(params.get("createEndTime")) ? null : params.get("createEndTime");
        }
        Long consumeBeginTime = null;
        Long consumeEndTime = null;
        // 消耗时间区间
        if(!StringUtils.isEmpty(params.get("consumeBeginTime")) || !StringUtils.isEmpty(params.get("consumeEndTime"))) {
            consumeBeginTime =
                    StringUtils.isEmpty(params.get("consumeBeginTime")) ? null : Long.parseLong(params.get("consumeBeginTime"));
            consumeEndTime =
                    StringUtils.isEmpty(params.get("consumeEndTime")) ? null : Long.parseLong(params.get("consumeEndTime"));
        }
        if (!StringUtils.isEmpty(params.get("ip"))) {
            userLog.setIp(params.get("ip"));
        }

        PageInfo<UserLog> pageInfo;
        if (userId == null && consumeBeginTime == null && consumeEndTime == null && createBeginTime == null &&
                createEndTime == null && userLog.getIp() == null && userLog.getMethod() == null){
            pageInfo =
                    userLogService.getAllUserLogPage(pageNum, pageSize);
        }else{
            pageInfo =
                    userLogService.getAllUserLogPageByCondition(pageNum,pageSize, userLog, createBeginTime, createEndTime, consumeBeginTime, consumeEndTime);
        }
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
