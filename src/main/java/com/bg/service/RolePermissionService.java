package com.bg.service;

import com.bg.entity.RolePermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ctp
 * @date 2022/12/3 2:52
 */
public interface RolePermissionService {
    Integer getRolePermissionId(Long userId, String pmName);
    List<Long> getPermissionByRoleId(Long roleId);
    boolean insertRolePermission(RolePermission rolePermission);
    boolean insertRolePermissionBatchByRole(Long roleId, List<Long> permissionIdList);
    boolean deleteRolePermissionById(RolePermission rolePermission);
}
