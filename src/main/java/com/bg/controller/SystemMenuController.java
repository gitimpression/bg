package com.bg.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.bg.entity.SystemMenu;
import com.bg.entity.User;
import com.bg.service.SystemMenuService;
import com.bg.util.ComRet;
import com.bg.util.JWTUtil;
import com.bg.util.SystemMenusUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/menu")
    public ComRet getSysMenu(@RequestHeader Map<String, String> headers) {
        // 前置拦截

        // 获取参数
        JSONObject userInfo = JSONObject.parseObject(headers.get("user_info"));
        User user = JSON.parseObject(userInfo.get("user").toString(), User.class);
        String token = userInfo.get("token").toString();
        Claims claims = JWTUtil.parse(token);
        if (claims == null){// 没有token或者token为空
            return ComRet.fail("无法获取登录状态");
        }
        long roleId = Long.parseLong(claims.get("roleId").toString());

        // 查询数据
        List<SystemMenu> menus = systemMenuService.getSystemMenuByRoleId(roleId);
        // 转换成多级菜单结构
        List<SystemMenu> menuList = SystemMenusUtil.multiMenus(menus);

        // 响应数据
        return ComRet.ok("查询菜单列表成功").add("menus", menuList);
    }
}
