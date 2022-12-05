package com.bg.mapper;

import com.bg.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author ctp
 * @date 2022/11/22 4:01
 */
public interface UserMapper {
    User getUserById(@Param("userId") Long id);
    User getUserByUsername(@Param("username") String username);
    User login(@Param("username") String username, @Param("password") String password);
    String getRoleName(@Param("userId") Long id);
    boolean updateUserHeadImg(@Param("userId") Long userId, @Param("head_img") String fileName);

    /**
     * 选择性更新
     * @param user user
     * @return return
     */
    Long updateUser(User user);
    boolean updateUserPassword(@Param("userId") Long userId, @Param("password") String password);
    boolean updateUserLoginTime(@Param("userId")Long userId, @Param("loginTime") String loginTime);

    boolean insertUser(User user);
}
