package com.bg.mapper;

import com.bg.entity.Permission;
import org.apache.ibatis.annotations.Param;

/**
 * @author ctp
 * @date 2022/12/3 2:44
 */
public interface PermissionMapper {
    Permission getPermissionByUserId(@Param("userId")Long userId);
}
