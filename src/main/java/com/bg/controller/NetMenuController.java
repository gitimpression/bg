package com.bg.controller;

import com.bg.anno.Log;
import com.bg.config.KeysProperties;
import com.bg.entity.NetMenu;
import com.bg.service.NetMenuService;
import com.bg.util.ComRet;
import com.bg.util.JwtUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 左侧导航 > 网络服务 > 菜单
 *
 * @author ctp
 * @date 2022/12/4 8:42
 */
@RestController
@RequestMapping("/netMenu")
public class NetMenuController {
    private static String topNotice = "暂无通知";// 顶部公告  注意每次重启服务器通知都会消失
    @Resource
    private NetMenuService netMenuService;

    @Log("查询网络服务菜单列表")
    @GetMapping
    public ComRet getAllNetMenu() {
        List<NetMenu> netMenuList = netMenuService.getAllNetMenu();
        return ComRet.ok().add("netMenuList", netMenuList);
    }

    @Log("查询网络服务顶部通知信息")
    @GetMapping("/notice")
    public ComRet getTopNotice() {
        return ComRet.ok().add("topNotice", topNotice);
    }

    @Log("修改网络服务顶部通知信息")
    @PostMapping("/notice")
    public ComRet updateTopNotice(@RequestBody Map<String,String> body) {
        topNotice = body.get("content");
        return ComRet.ok("修改成功").add("content", topNotice);
    }

    @Log("删除网络服务列表菜单")
    @DeleteMapping
    public ComRet deleteNetMenuById(@RequestBody Map<String, String> body) {
        String netMenuId = body.get("netMenuId");
        if (StringUtils.isEmpty(netMenuId)) {
            return ComRet.fail("参数有误");
        }
        boolean b = netMenuService.deleteNetMenuById(Long.parseLong(netMenuId));
        if (b) {
            return ComRet.ok("删除成功");
        } else {
            return ComRet.ok("删除失败");
        }
    }

    @Log("新增网络服务列表菜单")
    @PutMapping
    public ComRet deleteNetMenuById(@RequestHeader Map<String, String> headers, NetMenu netMenu) {
        if (netMenu == null) {
            return ComRet.fail("参数有误");
        }
        long userId = Long.parseLong(JwtUtil.claims(headers, KeysProperties.TOKEN_USER_ID_KEY));
        boolean b = netMenuService.insertNetMenu(netMenu);
        if (b) {
            return ComRet.ok("添加成功");
        }else{
            return ComRet.ok("添加失败");
        }
    }
}
