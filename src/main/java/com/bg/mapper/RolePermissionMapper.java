package com.bg.mapper;

import com.bg.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ctp
 * @date 2022/12/3 2:54
 */
public interface RolePermissionMapper {
    Integer getRolePermissionId(@Param("userId")Long userId, @Param("pmName")String pmName);
    List<Long> getPermissionByRoleId(@Param("roleId") Long roleId);
    boolean insertRolePermission(RolePermission rolePermission);
    boolean deleteRolePermission(RolePermission rolePermission);
}
