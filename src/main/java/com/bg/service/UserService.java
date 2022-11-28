package com.bg.service;

import com.bg.entity.User;

/**
 * @author ctp
 * @date 2022/11/22 4:01
 */
public interface UserService {
    User getUserById(Integer id);
    User login(String username, String password);
    String getRoleName(Long id);
    boolean changeUserHeadImg(Long userId, String fileName);
}
