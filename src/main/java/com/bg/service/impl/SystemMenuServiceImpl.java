package com.bg.service.impl;

import com.bg.entity.SystemMenu;
import com.bg.mapper.SystemMenuMapper;
import com.bg.service.SystemMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ctp
 * @date 2022/11/23 22:07
 */
@Service
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
}
