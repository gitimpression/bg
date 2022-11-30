package com.bg.controller;

import com.bg.anno.Log;
import com.bg.util.ComRet;
import com.bg.util.VerifyCodeImg;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 公共控制层，如请求验证码
 * @author ctp
 * @date 2022/11/30 10:26
 */
@RestController
public class CommonController {
    /**
     * 获取图片验证码
     *
     * @param session session存放验证码值
     * @return return
     */
    @Log("获取图片验证码")
    @GetMapping("/verifyCodeImg")
    public ComRet getVerifyCodeImg(HttpSession session) {
        VerifyCodeImg codeImg = new VerifyCodeImg();
        session.setAttribute("verifyCode", codeImg.getCode());
        session.setMaxInactiveInterval(60); // 60秒失效
        return ComRet.ok().add("data", codeImg.getImg());
    }
}
