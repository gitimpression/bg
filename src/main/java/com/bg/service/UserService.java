package com.bg.service;

import com.bg.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author ctp
 * @date 2022/11/22 4:01
 */
public interface UserService {
    User getUserById(Long id);
    User getUserByUsername(String username);
    User login(String username, String password);
    String getRoleName(Long id);
    boolean updateUserHeadImg(Long userId, String fileName);
    long updateUser(User user);
    boolean updateUserPassword(Long userId, String password);
    boolean updateUserLoginTime(@Param("id") Long userId, String loginTime);
    boolean insertUser(User user);
}
