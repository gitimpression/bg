package com.bg.controller;

import com.bg.anno.Log;
import com.bg.config.KeysProperties;
import com.bg.entity.SystemMenu;
import com.bg.service.SystemMenuService;
import com.bg.util.ComRet;
import com.bg.util.JwtUtil;
import com.bg.util.SystemMenusUtil;
import com.sun.istack.internal.NotNull;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 处理请求 bg系统参数，如系统菜单列表数据
 * @author ctp
 * @date 2022/11/23 18:20
 */

@RestController
@RequestMapping("/sys")
public class SystemMenuController {

    @Resource
    private SystemMenuService systemMenuService;

    @Log("按权限获取菜单列表")
    @GetMapping("/menu")
    public ComRet getSysMenu(@RequestHeader Map<String, String> headers) {
        long roleId = Long.parseLong(JwtUtil.claims(headers, KeysProperties.TOKEN_ROLE_ID_KEY));
        // 查询数据
        List<SystemMenu> menus = systemMenuService.getSystemMenuByRoleId(roleId)
                                .stream().filter(menu -> "true".equals(menu.getEnabled()))
                                .collect(Collectors.toList());
        // 转换成多级菜单结构
        List<SystemMenu> menuList = SystemMenusUtil.multiMenus(menus);

        // 响应数据
        return ComRet.ok("查询菜单列表成功").add("menus", menuList);
    }

    @Log("获取菜单列表")
    @GetMapping("/menu/all")
    public ComRet getAllSystemMenu() {
        // 查询数据
        List<SystemMenu> menus = systemMenuService.getAllSystemMenu();
        // 响应数据
        return ComRet.ok("查询菜单列表成功").add("menus", menus);
    }


    @Log("修改系统菜单列表")
    @PostMapping("/menu")
    public ComRet updateSystemMenu(@RequestBody SystemMenu systemMenu) {
        if (systemMenu == null || systemMenu.getId() == null){
            return ComRet.fail("修改失败");
        }
        // 查询数据
        boolean b = systemMenuService.updateSystemMenu(systemMenu);
        if (b){
            return ComRet.ok("修改成功");
        }else{
            return ComRet.fail("修改失败");
        }
    }

    @Log("修改系统菜单列表")
    @DeleteMapping("/menu")
    public ComRet deleteSystemMenu(@RequestBody Map<String,String> headers) {
        String systemMenuId = headers.get("systemMenuId");
        if (StringUtils.isEmpty(systemMenuId)){
            return ComRet.fail("删除失败");
        }
        // 查询数据
        boolean b = systemMenuService.deleteSystemMenu(Long.parseLong(systemMenuId));
        if (b){
            return ComRet.ok("删除成功");
        }else{
            return ComRet.fail("删除失败");
        }
    }


    @Log("添加系统菜单列表")
    @PutMapping("/menu")
    public ComRet insertSystemMenu(@RequestBody SystemMenu systemMenu) {
        if (systemMenu == null){
            return ComRet.fail("添加失败");
        }
        // 查询数据
        boolean b = systemMenuService.insertSystemMenu(systemMenu);
        if (b){
            return ComRet.ok("添加成功");
        }else{
            return ComRet.fail("添加失败");
        }
    }
}
