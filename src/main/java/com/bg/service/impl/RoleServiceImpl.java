package com.bg.service.impl;

import com.bg.entity.Role;
import com.bg.mapper.RoleMapper;
import com.bg.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ctp
 * @date 2022/12/3 2:52
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Override
    public List<Role> getAllRole() {
        return roleMapper.getAllRole();
    }

    @Override
    public boolean insertRole(Role role) {
        return roleMapper.insertRole(role);
    }

    @Override
    public boolean deleteRole(Long roleId) {
        return roleMapper.deleteRole(roleId);
    }

    @Override
    public boolean updateRole(Role role) {
        return roleMapper.updateRole(role);
    }
}
