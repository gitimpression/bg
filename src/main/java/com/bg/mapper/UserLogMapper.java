package com.bg.mapper;

import com.bg.entity.SimpleUserLog;
import com.bg.entity.UserLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户日志接口
 * 因为开启了分页插件，查询时不需要携带页码和页长，插件自动添加
 *
 * @author ctp
 * @date 2022/11/29 23:30
 */
public interface UserLogMapper {
    boolean insertUserLog(UserLog userLog);

    // 只查询用户的更新操作记录
    List<SimpleUserLog> getSimpleUserLog(@Param("userId") Long userId, @Param("method") String method);

    // 只查询用户的登录记录
    List<SimpleUserLog> getSimpleUserLoginLog(@Param("userId") Long userId, @Param("method") String method);

    // 获取所有用户日志分页数据
    List<UserLog> getAllUserLogPage(@Param("userId") Long userId);

    // 获取某个用户日志分页数据
    List<UserLog> getUserLogPage(@Param("userId") Long userId);
}
