package com.bg.service.impl;

import com.bg.entity.User;
import com.bg.handler.CustomException;
import com.bg.mapper.UserMapper;
import com.bg.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author ctp
 * @date 2022/11/22 4:01
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    @Override
    public User login(String username, String password) {
        return userMapper.login(username, password);
    }

    @Override
    public String getRoleName(Long id) {
        return userMapper.getRoleName(id);
    }

    @Override
    public boolean changeUserHeadImg(Long userId, String fileName) {
        return userMapper.changeUserHeadImg(userId, fileName);
    }

    @Override
    public long updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public boolean changeUserPassword(Long userId, String password) {
        return userMapper.changeUserPassword(userId, password);
    }

}
