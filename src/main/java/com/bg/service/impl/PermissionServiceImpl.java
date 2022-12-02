package com.bg.service.impl;

import com.bg.entity.Permission;
import com.bg.mapper.PermissionMapper;
import com.bg.service.PermissionService;

import javax.annotation.Resource;

/**
 * 权限服务
 * @author ctp
 * @date 2022/11/27 22:05
 */
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;
    @Override
    public Permission getPermissionByUserId(Long userId) {
        return null;
    }
}
