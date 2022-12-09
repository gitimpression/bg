package com.bg.controller;

import com.bg.anno.Log;
import com.bg.config.KeysProperties;
import com.bg.entity.User;
import com.bg.service.RolePermissionService;
import com.bg.service.UserService;
import com.bg.util.*;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author ctp
 * @date 2022/11/22 3:57
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private RolePermissionService rolePermissionService;

    @Log("获取用户信息")
    @GetMapping()
    public ComRet getUserInfo(@RequestHeader Map<String, String> headers) {
        Long userId = Long.parseLong(JwtUtil.claims(headers, KeysProperties.TOKEN_USER_ID_KEY));
        User user = userService.getUserById(userId);
        return ComRet.ok("查询成功").add("user", user);
    }

    @Log("修改用户基本信息")
    @PostMapping
    public ComRet updateUserInfo(@RequestHeader Map<String, String> headers,
                                 @RequestBody User user) {

        // 只能修改可以修改的
        user.setCreateTime(null);
        user.setLoginTime(null);
        user.setHeadImg(null); // 另外一个请求修改用户头像
        user.setPassword(null); // 另外一个请求修改用户密码

        // TODO 属于权限管理部分，增加权限判断，判断是否能修改 角色ID 和 用户状态
        user.setRoleId(null);
        user.setUserStatus(null);

        // TODO 内容是否全部为空

        if (!StringUtils.isEmpty(user.getUsername())){
            if (!UserUtil.checkUsername(user.getUsername())){
                return ComRet.fail("修改失败,请检查用户名的合法性");
            }
        }
        if (!StringUtils.isEmpty(user.getPassword())){
            if (!UserUtil.checkPassword(user.getPassword())){
                return ComRet.fail("修改失败,请检查用户密码的合法性");
            }
        }
        if (!StringUtils.isEmpty(user.getEmail())){
            if (!UserUtil.checkEmail(user.getEmail())){
                return ComRet.fail("修改失败,请检查用户邮箱的合法性");
            }
        }

        long userId = Long.parseLong(JwtUtil.claims(headers, KeysProperties.TOKEN_USER_ID_KEY));

        user.setId(userId);

        long row = userService.updateUser(user);
        if (row == 1){
            return ComRet.ok("修改成功");
        }else{
            return ComRet.fail("修改失败," + row);
        }
    }

    @Log("修改用户头像")
    @PostMapping("/headImg")
    public ComRet updateUserHeadImg(@RequestHeader Map<String, String> headers,
                                 @RequestBody String fileName) {
        Long userId = Long.parseLong(JwtUtil.claims(headers, KeysProperties.TOKEN_USER_ID_KEY));
        fileName = fileName.replaceAll("\"", "");
        boolean b = userService.updateUserHeadImg(userId, fileName);
        if (b) {
            return ComRet.ok("修改头像成功").add("headImg", fileName);
        } else {
            return ComRet.fail("修改头像失败");
        }
    }

    @Log("修改用户密码")
    @PostMapping("/password")
    public ComRet updateUserPassword(@RequestHeader Map<String, String> headers,
                                     @RequestBody Map<String,String> map) {
        String password = map.get(KeysProperties.USER_PASSWORD_KEY);
        if (StringUtils.isEmpty(password)){
            return ComRet.fail("修改失败,密码不能为空");
        }
        if (!UserUtil.checkPassword(password)){
            return ComRet.fail("修改失败,密码格式不正确");
        }
        Long userId = Long.parseLong(JwtUtil.claims(headers, KeysProperties.TOKEN_USER_ID_KEY));
        if (userService.updateUserPassword(userId, DigestUtils.md5DigestAsHex(password.getBytes()))){
            return ComRet.ok("修改密码成功");
        }else{
            return ComRet.fail("修改失败");
        }
    }

    @Log("用户登录")
    @PostMapping("/login")
    public ComRet login(@RequestBody Map<String, String> map, HttpSession session) {
        String username = map.get("username");
        String password = map.get("password");
        String code = map.get("code");
        boolean remember = Boolean.parseBoolean(map.get("remember"));
        // 验证码
        String verifyCode = (String) session.getAttribute("verifyCode");
        if (StringUtils.isEmpty(verifyCode)) {// 验证码过期
            return ComRet.fail("验证码已过期");
        }
        if (StringUtils.isEmpty(code)) {// 格式不正确
            String regx = "^[a-zA-Z0-9]{4}$";
            boolean b = code.matches(regx);
            if (!b) {// 格式不匹配
                return ComRet.fail("验证码格式错误");
            }
            if (!code.toUpperCase().equals(verifyCode.toUpperCase())) { // 验证码不正确
                return ComRet.fail("验证码错误");
            }
        }
        // 用户
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {// 空信息
            return ComRet.fail("账号或密码不能为空");
        }
        User loginUser = userService.login(username, DigestUtils.md5DigestAsHex(password.getBytes()));
        if (loginUser == null) {    // 不存在用户
            return ComRet.fail("密码不正确或用户不存在");
        }
        String token = JwtUtil.createToken(loginUser, remember);
        if (StringUtils.isEmpty(token)) {   // 创建token失败
            return ComRet.fail("创建登录状态失败");
        }
        // 更新登录时间
        userService.updateUserLoginTime(loginUser.getId(), DateTimeUtil.getDataTime());
        return ComRet.ok("登录成功").add("data", token);
    }

    @Log("用户退出登录")
    @PostMapping("/logout")
    public ComRet logout() {
        return ComRet.ok("退出登录成功");
    }

    @Log("用户注册")
    @PutMapping("/register")
    public ComRet register(@RequestBody User user) {
        if (user == null){
            return ComRet.fail("参数有误");
        }
        if (UserUtil.checkUsername(user.getUsername()) && UserUtil.checkPassword(user.getPassword())){
            if (userService.getUserByUsername(user.getUsername()) != null){
                return ComRet.fail("用户名重复");
            }
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            boolean b = userService.insertUser(user);
            if (b){
                return ComRet.ok("注册成功");
            }else{
                return ComRet.fail("注册失败");
            }
        }else{
            return ComRet.fail("注册失败");
        }
    }

    @Log("获取用户角色名")
    @GetMapping("/role")
    public ComRet getRole(@RequestHeader Map<String, String> headers) {
        String roleId = JwtUtil.claims(headers, KeysProperties.TOKEN_ROLE_ID_KEY);
        String name = userService.getRoleName(Long.parseLong(roleId));
        return ComRet.ok("查询成功").add("data", name);
    }

    /**
     * 获取用户对应权限
     * @param headers headers
     * @param pmName 权限名
     * @return return
     */
    @Log("获取用户对应权限")
    @GetMapping("/pm")
    public ComRet getPermissionByRoleId(@RequestHeader Map<String, String> headers,String pmName) {
        Long userId = Long.parseLong(JwtUtil.claims(headers, KeysProperties.TOKEN_USER_ID_KEY));
        if (StringUtils.isEmpty(pmName)){
            return ComRet.fail("参数有问题");
        }
        Integer permissionId = rolePermissionService.getRolePermissionId(userId, pmName);
        return ComRet.ok().add("data", permissionId != null);
    }

    /**
     * 检查登录状态是否过期
     * @param headers headers
     * @return return
     */
    @Log("检查登录状态是否过期")
    @GetMapping("/expire")
    public ComRet getIsExpireByToken(@RequestHeader Map<String, String> headers){
        try {
            JwtUtil.parse(headers.get(KeysProperties.TOKEN_KEY));
            return ComRet.ok().add("isExpire",false);
        }catch (Exception e){
            return ComRet.fail().add("isExpire",true);
        }
    }

    /**
     * 搜索用户
     * @param type 类型 id/用户名/邮箱
     * @param content 输入的内容
     * @return 用户
     */
    @Log("搜索用户")
    @GetMapping("/search")
    public ComRet getUser(String type, String content){
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(content)){
            return ComRet.fail("缺少参数");
        }
        switch (type){
            case "userId":
                User userById = userService.getUserById(Long.parseLong(content));
                userById.setPassword(null);
                return ComRet.ok().add("user", userById);
            case "username":
                User userByUsername = userService.getUserByUsername(content);
                userByUsername.setPassword(null);;
                return ComRet.ok().add("user", userByUsername);
            case "email":
                User userByEmail = userService.getUserByEmail(content);
                userByEmail.setPassword(null);
                return ComRet.ok().add("user", userByEmail);
            default:
                return ComRet.fail("参数有误");
        }
    }
}
