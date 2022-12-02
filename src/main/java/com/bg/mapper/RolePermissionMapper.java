package com.bg.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @author ctp
 * @date 2022/12/3 2:54
 */
public interface RolePermissionMapper {
    Integer getRolePermissionId(@Param("userId")Long userId, @Param("pmName")String pmName);
}
