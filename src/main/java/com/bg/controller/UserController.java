package com.bg.controller;

import com.bg.entity.User;
import com.bg.service.UserService;
import com.bg.util.ComRet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
