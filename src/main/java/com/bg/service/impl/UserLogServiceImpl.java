package com.bg.service.impl;

import com.bg.config.KeysProperties;
import com.bg.entity.SimpleUserLog;
import com.bg.entity.UserLog;
import com.bg.mapper.UserLogMapper;
import com.bg.service.UserLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户日志服务
 *
 * @author ctp
 * @date 2022/11/29 23:37
 */
@Service
@Transactional
public class UserLogServiceImpl implements UserLogService {
    @Resource
    private UserLogMapper userLogMapper;

    @Override
    public boolean insertUserLog(UserLog userLog) {
        return userLogMapper.insertUserLog(userLog);
    }

    @Override
    public PageInfo<SimpleUserLog> getSimpleUserLog(Long userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);  // 查询前设置拦截,以便分页管理
        Long start = (pageNum - 1) * Long.parseLong(pageSize.toString());
        List<SimpleUserLog> userLogList = userLogMapper.getSimpleUserLog(userId, KeysProperties.SQL_USER_LOG_METHOD_LOGIN);
        return new PageInfo<>(userLogList, 5);
    }

    @Override
    public PageInfo<SimpleUserLog> getSimpleUserLoginLog(Long userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Long start = (pageNum - 1) * Long.parseLong(pageSize.toString());
        List<SimpleUserLog> userLogList = userLogMapper.getSimpleUserLoginLog(userId, KeysProperties.SQL_USER_LOG_METHOD_LOGIN);
        return new PageInfo<SimpleUserLog>(userLogList, 5);
    }

    @Override
    public PageInfo<UserLog> getAllUserLogPage(Long userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Long start = (pageNum - 1) * Long.parseLong(pageSize.toString());
        List<UserLog> userLogList = userLogMapper.getAllUserLogPage(userId);
        return new PageInfo<>(userLogList, 5);
    }

    @Override
    public PageInfo<UserLog> getUserLogPage(Long userId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Long start = (pageNum - 1) * Long.parseLong(pageSize.toString());
        List<UserLog> userLogList = userLogMapper.getUserLogPage(userId);
        return new PageInfo<>(userLogList, 5);
    }

}
