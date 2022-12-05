package com.bg.mapper;

import com.bg.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ctp
 * @date 2022/12/3 2:54
 */
public interface RoleMapper {
    List<Role> getAllRole();
    boolean insertRole(Role role);
    boolean deleteRole(@Param("roleId") Long roleId);
    boolean updateRole(Role role);
}
