package com.bg.controller;

import com.bg.config.KeysProperties;
import com.bg.entity.User;
import com.bg.service.UserService;
import com.bg.util.ComRet;
import com.bg.util.JwtUtil;
import com.bg.util.VerifyCodeImg;
import io.jsonwebtoken.Claims;
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
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 获取用户信息
     * @param headers 请求头以便获取token
     * @return 用户信息
     */
    @GetMapping("/user")
    public ComRet getUserInfo(@RequestHeader Map<String, String> headers) {
        Claims claims = JwtUtil.parse(headers.get(KeysProperties.TOKEN_KEY));
        Integer userId = Integer.parseInt(claims.get(KeysProperties.TOKEN_USER_ID_KEY).toString());
        User user = userService.getUserById(userId);
        return ComRet.ok("查询成功").add("user", user);
    }

    /**
     * 修改头像
     * @param headers 请求头以便获取token
     * @return 头像URL
     */
    @PostMapping("/user/headImg")
    public ComRet changeUserInfo(@RequestHeader Map<String, String> headers,
                                 @RequestBody String fileName) {
        Long userId = Long.parseLong(JwtUtil.claims(headers, KeysProperties.TOKEN_USER_ID_KEY));
        boolean b = userService.changeUserHeadImg(userId, fileName);
        if (b) {
            return ComRet.ok("修改头像成功").add("headImg", fileName);
        } else {
            return ComRet.fail("修改头像失败");
        }
    }

    /**
     * 用户登录
     * @param map     请求体
     * @param session session
     * @return return
     */
    @PostMapping("/user/login")
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
        return ComRet.ok("登录成功").add("data", token);
    }

    /**
     * 用户退出登录
     * @param headers headers
     * @return return
     */
    @PostMapping("/user/logout")
    public ComRet logout(@RequestHeader Map<String, String> headers) {
        // TODO 日志记录
        return ComRet.ok("退出登录成功");
    }

    /**
     * 获取用户roleId对应的角色名
     * @param headers 请求头
     * @return 角色名
     */
    @GetMapping("/user/role")
    public ComRet getRole(@RequestHeader Map<String, String> headers) {
        String roleId = JwtUtil.claims(headers, KeysProperties.TOKEN_ROLE_ID_KEY);
        String name = userService.getRoleName(Long.parseLong(roleId));
        return ComRet.ok("查询成功").add("data", name);
    }

    /**
     * 获取图片验证码
     * @param session session存放验证码值
     * @return return
     */
    @GetMapping("/verifyCodeImg")
    public ComRet getVerifyCodeImg(HttpSession session) {
        VerifyCodeImg codeImg = new VerifyCodeImg();
        session.setAttribute("verifyCode", codeImg.getCode());
        session.setMaxInactiveInterval(60); // 60秒失效
        return ComRet.ok().add("data", codeImg.getImg());
    }
}