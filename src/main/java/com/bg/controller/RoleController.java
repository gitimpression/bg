package com.bg.controller;

import com.bg.anno.Log;
import com.bg.entity.Role;
import com.bg.service.RoleService;
import com.bg.util.ComRet;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author ctp
 * @date 2022/12/5 9:41
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @Log("查询所有角色记录")
    @GetMapping
    public ComRet getAllRole(){
        List<Role> roleList = roleService.getAllRole();
        return ComRet.ok().add("roleList", roleList);
    }

    @Log("删除角色记录")
    @DeleteMapping
    public ComRet deleteRole(@RequestBody Map<String,String> body){
        String roleId = body.get("roleId");
        if (StringUtils.isEmpty(roleId)){
            return ComRet.fail("参数有误");
        }
        boolean b = roleService.deleteRole(Long.parseLong(roleId));
        if (b){
            return ComRet.ok("删除成功");
        }else{
            return ComRet.fail("删除失败");
        }
    }

    @Log("添加角色记录")
    @PutMapping
    public ComRet insertPermission(@RequestBody Role role){
        if (role == null){
            return ComRet.fail("参数有误");
        }
        boolean b = roleService.insertRole(role);
        if (b){
            return ComRet.ok("添加成功");
        }else{
            return ComRet.fail("添加失败");
        }
    }

    @Log("修改角色记录")
    @PostMapping
    public ComRet updatePermission(@RequestBody Role role){
        if (role == null){
            return ComRet.fail("参数有误");
        }
        boolean b = roleService.updateRole(role);
        if (b){
            return ComRet.ok("修改成功");
        }else{
            return ComRet.fail("修改失败");
        }
    }
}
