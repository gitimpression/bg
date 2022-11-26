package com.bg.mapper;

import com.bg.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author ctp
 * @date 2022/11/22 4:01
 */
public interface UserMapper {
    User getUserById(@Param("id") Integer id);
    User login(@Param("username") String username, @Param("password") String password);
    String getRoleName(Long id);
}
