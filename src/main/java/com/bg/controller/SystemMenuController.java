package com.bg.controller;

import com.bg.anno.Log;
import com.bg.config.KeysProperties;
import com.bg.entity.SystemMenu;
import com.bg.service.SystemMenuService;
import com.bg.util.ComRet;
import com.bg.util.JwtUtil;
import com.bg.util.SystemMenusUtil;
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

    @Log("获取菜单列表")
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
}
