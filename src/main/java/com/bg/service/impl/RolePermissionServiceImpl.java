package com.bg.service.impl;

import com.bg.mapper.RolePermissionMapper;
import com.bg.service.RolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author ctp
 * @date 2022/12/3 2:52
 */
@Service
@Transactional
public class RolePermissionServiceImpl implements RolePermissionService {
    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public Integer getRolePermissionId(Long userId, String pmName) {
        return rolePermissionMapper.getRolePermissionId(userId, pmName);
    }
}
