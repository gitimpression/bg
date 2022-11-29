package com.bg.mapper;

import com.bg.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author ctp
 * @date 2022/11/22 4:01
 */
public interface UserMapper {
    User getUserById(@Param("id") Long id);
    User login(@Param("username") String username, @Param("password") String password);
    String getRoleName(Long id);
    boolean changeUserHeadImg(@Param("id") Long userId, @Param("head_img") String fileName);

    /**
     * 选择性更新
     * @param user user
     * @return return
     */
    Long updateUser(User user);
    boolean changeUserPassword(@Param("id") Long userId, @Param("password") String password);
}
