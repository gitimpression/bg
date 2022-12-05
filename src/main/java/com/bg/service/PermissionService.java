package com.bg.service;

import com.bg.entity.Permission;

import java.util.List;

/**
 * 权限服务
 * @author ctp
 * @date 2022/11/27 22:05
 */
public interface PermissionService {
    Permission getPermissionByUserId(Long userId);
    List<Permission> getAllPermission();
    boolean insertPermission(Permission permission);
    boolean deletePermission(Long permissionId);
    boolean updatePermission(Permission permission);
}
