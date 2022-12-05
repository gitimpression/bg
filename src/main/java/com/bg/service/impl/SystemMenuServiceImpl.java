package com.bg.service.impl;

import com.bg.entity.SystemMenu;
import com.bg.mapper.SystemMenuMapper;
import com.bg.service.SystemMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ctp
 * @date 2022/11/23 22:07
 */
@Service
@Transactional
public class SystemMenuServiceImpl implements SystemMenuService {
    @Resource
    private SystemMenuMapper systemMenuMapper;

    /**
     * 根据id查询可用菜单
     * @param roleId 用户的角色id
     * @return List<SystemMenu>
     */
    @Override
    public List<SystemMenu> getSystemMenuByRoleId(Long roleId) {
        return systemMenuMapper.getSystemMenuByRoleId(roleId);
    }

    @Override
    public List<SystemMenu> getAllSystemMenu() {
        return systemMenuMapper.getAllSystemMenu();
    }

    @Override
    public boolean updateSystemMenu(SystemMenu systemMenu) {
        return systemMenuMapper.updateSystemMenu(systemMenu);
    }

    @Override
    public boolean deleteSystemMenu(Long systemMenuId) {
        return systemMenuMapper.deleteSystemMenu(systemMenuId);
    }

    @Override
    public boolean insertSystemMenu(SystemMenu systemMenu) {
        return systemMenuMapper.insertSystemMenu(systemMenu);
    }
}
