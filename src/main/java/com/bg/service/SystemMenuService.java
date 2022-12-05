package com.bg.service;

import com.bg.entity.SystemMenu;

import java.util.List;

/**
 * @author ctp
 * @date 2022/11/23 22:06
 */
public interface SystemMenuService {
    List<SystemMenu> getSystemMenuByRoleId(Long roleId);
    List<SystemMenu> getAllSystemMenu();
    boolean updateSystemMenu(SystemMenu systemMenu);
    boolean deleteSystemMenu(Long systemMenuId);
    boolean insertSystemMenu(SystemMenu systemMenu);
}