package com.bg.controller;

import com.bg.anno.Log;
import com.bg.service.RolePermissionService;
import com.bg.util.ComRet;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author ctp
 * @date 2022/12/3 2:51
 */
@RestController
@RequestMapping("/rolePm")
public class RolePermissionController {
    @Resource
    private RolePermissionService rolePermissionService;

    @Log("根据角色id获取对应权限")
    @GetMapping("/{roleId}")
    public ComRet a(@PathVariable("roleId")Long roleId){
        if (roleId == null){
            return ComRet.fail("参数有误");
        }
        List<Long> pmByRoleList = rolePermissionService.getPermissionByRoleId(roleId);
        return ComRet.ok().add("pmByRoleList", pmByRoleList);
    }

    @Log("更新角色拥有的权限")
    @PostMapping("/{roleId}")
    public ComRet a(@PathVariable("roleId")Long roleId,
                    @RequestBody Map<String,List<Long>> body){
        List<Long> frontIdList = body.get("idList");  // 前端获取到的权限id列表
        if (roleId == null || frontIdList == null || frontIdList.size() < 1){
            return ComRet.fail("参数有误");
        }
        boolean b = rolePermissionService.insertRolePermissionBatchByRole(roleId, frontIdList);
        if (b){
            return ComRet.ok("操作成功");
        }else{
            return ComRet.fail("操作失败");
        }
    }
}
