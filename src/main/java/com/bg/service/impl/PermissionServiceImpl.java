package com.bg.service.impl;

import com.bg.entity.Permission;
import com.bg.mapper.PermissionMapper;
import com.bg.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限服务
 * @author ctp
 * @date 2022/11/27 22:05
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;
    @Override
    public Permission getPermissionByUserId(Long userId) {
        return null;
    }

    @Override
    public List<Permission> getAllPermission() {
        return permissionMapper.getAllPermission();
    }

    @Override
    public boolean insertPermission(Permission permission) {
        return permissionMapper.insertPermission(permission);
    }

    @Override
    public boolean deletePermission(Long permissionId) {
        return permissionMapper.deletePermission(permissionId);
    }

    @Override
    public boolean updatePermission(Permission permission) {
        return permissionMapper.updatePermission(permission);
    }

}
