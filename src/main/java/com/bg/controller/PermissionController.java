package com.bg.controller;

import com.bg.anno.Log;
import com.bg.entity.Permission;
import com.bg.service.PermissionService;
import com.bg.util.ComRet;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author ctp
 * @date 2022/12/3 2:37
 */
@RestController
@RequestMapping("/pm")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @Log("查询所有权限记录")
    @GetMapping
    public ComRet getAllPermission(){
        List<Permission> permissionList = permissionService.getAllPermission();
        return ComRet.ok().add("permissionList", permissionList);
    }

    @Log("删除权限记录")
    @DeleteMapping
    public ComRet deletePermission(@RequestBody Map<String,String> body){
        String permissionId = body.get("permissionId");
        if (StringUtils.isEmpty(permissionId)){
            return ComRet.fail("参数有误");
        }
        boolean b = permissionService.deletePermission(Long.parseLong(permissionId));
        if (b){
            return ComRet.ok("删除成功");
        }else{
            return ComRet.fail("删除失败");
        }
    }
    @Log("添加权限记录")
    @PutMapping
    public ComRet insertPermission(@RequestBody Permission permission){
        if (permission == null){
            return ComRet.fail("参数有误");
        }
        boolean b = permissionService.insertPermission(permission);
        if (b){
            return ComRet.ok("添加成功");
        }else{
            return ComRet.fail("添加失败");
        }
    }


    @Log("修改权限记录")
    @PostMapping
    public ComRet updatePermission(@RequestBody Permission permission){
        if (permission == null){
            return ComRet.fail("参数有误");
        }
        boolean b = permissionService.updatePermission(permission);
        if (b){
            return ComRet.ok("修改成功");
        }else{
            return ComRet.fail("修改失败");
        }
    }

}
