package com.bg.service;

import com.bg.entity.Permission;

/**
 * 权限服务
 * @author ctp
 * @date 2022/11/27 22:05
 */
public interface PermissionService {
    Permission getPermissionByUserId(Long userId);
}
