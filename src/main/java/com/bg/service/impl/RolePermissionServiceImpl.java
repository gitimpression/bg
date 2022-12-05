package com.bg.service.impl;

import com.bg.entity.RolePermission;
import com.bg.mapper.RolePermissionMapper;
import com.bg.service.RolePermissionService;
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
public class RolePermissionServiceImpl implements RolePermissionService {
    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public Integer getRolePermissionId(Long userId, String pmName) {
        return rolePermissionMapper.getRolePermissionId(userId, pmName);
    }

    @Override
    public List<Long> getPermissionByRoleId(Long roleId) {
        return rolePermissionMapper.getPermissionByRoleId(roleId);
    }

    @Override
    public boolean insertRolePermission(RolePermission rolePermission) {
        return rolePermissionMapper.insertRolePermission(rolePermission);
    }

    /**
     *
     * @param roleId 角色id
     * @param frontPermissionIdList 前端传过来的权限id集合
     * @return return
     */
    @Override
    public boolean insertRolePermissionBatchByRole(Long roleId, List<Long> frontPermissionIdList) {
        List<Long> dbPermissionIdList = getPermissionByRoleId(roleId);
        // 分两步
        // 1 前端有 后端没有 -> 增加
        frontPermissionIdList.forEach(id -> {  // 遍历前端数据
            if (!dbPermissionIdList.contains(id)) {  // 后端没有  插入
                insertRolePermission(new RolePermission(null, roleId, id));
            }
        });
        // 2 前端没有  后端有 -> 删除
        dbPermissionIdList.forEach(id -> {// 遍历后端数据
            if (!frontPermissionIdList.contains(id)){// 前端没有  删除
                deleteRolePermissionById(new RolePermission(null, roleId, id));
            }
        });
        return true;
    }

    @Override
    public boolean deleteRolePermissionById(RolePermission rolePermission) {
        return rolePermissionMapper.deleteRolePermission(rolePermission);
    }

}
