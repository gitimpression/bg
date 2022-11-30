package com.bg.service;

import com.bg.entity.SimpleUserLog;
import com.bg.entity.UserLog;
import com.github.pagehelper.PageInfo;

/**
 * 用户日志服务
 *
 * @author ctp
 * @date 2022/11/29 23:36
 */
public interface UserLogService {
    boolean insertUserLog(UserLog userLog);

    PageInfo<SimpleUserLog> getSimpleUserLog(Long userId, Integer pageNum, Integer pageSize);

    PageInfo<SimpleUserLog> getSimpleUserLoginLog(Long userId, Integer pageNum, Integer pageSize);

    // 查询所有用户日志记录
    PageInfo<UserLog> getAllUserLogPage(Long userId, Integer pageNum, Integer pageSize);

    // 查询指定用户日志记录
    PageInfo<UserLog> getUserLogPage(Long userId, Integer pageNum, Integer pageSize);

}
