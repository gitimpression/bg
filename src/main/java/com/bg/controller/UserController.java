package com.bg.controller;

import com.bg.entity.User;
import com.bg.service.UserService;
import com.bg.util.ComRet;
import com.bg.util.JWTUtil;
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

    @GetMapping("/user/{id}")
    public ComRet getUserById(@PathVariable("id")Integer id){
        User user = userService.getUserById(id);
        return ComRet.ok().add("user", user);
    }

    @PostMapping("/user/login")
    public ComRet login(@RequestBody Map<String,String> map, HttpSession session){
        String username = map.get("username");
        String password = map.get("password");
        String code = map.get("code");
        boolean remember = Boolean.parseBoolean(map.get("remember"));
        // 验证码
        String verifyCode = (String)session.getAttribute("verifyCode");
        if (StringUtils.isEmpty(verifyCode)){// 验证码过期
            return ComRet.fail("验证码已过期");
        }
        if (StringUtils.isEmpty(code)){// 格式不正确
            String regx = "^[a-zA-Z0-9]{4}$";
            boolean b = code.matches(regx);
            if (!b){// 格式不匹配
                return ComRet.fail("验证码格式错误");
            }
            if (!code.toUpperCase().equals(verifyCode.toUpperCase())){ // 验证码不正确
                return ComRet.fail("验证码错误");
            }
        }
        // 用户
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){// 空信息
            return ComRet.fail("账号或密码不能为空");
        }

        User loginUser = userService.login(username, DigestUtils.md5DigestAsHex(password.getBytes()));
        if (loginUser == null){// 不存在用户
            return ComRet.fail("密码不正确或用户不存在");
        }
        String token = JWTUtil.createToken(loginUser, remember);
        if (StringUtils.isEmpty(token)){// 创建token失败
            return ComRet.fail("创建登录状态失败");
        }
        return ComRet.ok()
                .add("user", loginUser)
                .add("token", token);
    }

    @GetMapping("/verifyCodeImg")
    public ComRet getVerifyCodeImg(HttpSession session) {
        VerifyCodeImg codeImg = new VerifyCodeImg();
        session.setAttribute("verifyCode", codeImg.getCode());
        return ComRet.ok().add("data", codeImg.getImg());
    }

    @GetMapping("/test")
    public ComRet test(){
        Claims parse = JWTUtil.parse("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImV4cCI6MTY2OTExMjIzOSwianRpIjoiZTYwMGM2ZjctM2I2Ni00NTc1LWEyNjQtMWU4YjQ1N2VlZjhiIn0.624XO2X58oBxa0fwOex2F_33ApY4weVmSNwv6BK1H_0");
        return ComRet.ok("userId",parse.get("userId"));
    }
}
