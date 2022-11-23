package com.bg.mapper;

import com.bg.entity.SystemMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ctp
 * @date 2022/11/23 21:36
 */
public interface SystemMenuMapper {
    List<SystemMenu> getSystemMenuByRoleId(@Param("roleId")Long roleId);
}
