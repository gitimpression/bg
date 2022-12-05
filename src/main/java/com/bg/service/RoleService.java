package com.bg.service;

import com.bg.entity.Role;

import java.util.List;

/**
 * @author ctp
 * @date 2022/12/3 2:51
 */
public interface RoleService {
    List<Role> getAllRole();
    boolean insertRole(Role role);
    boolean deleteRole(Long roleId);
    boolean updateRole(Role role);
}
