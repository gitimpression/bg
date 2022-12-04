package com.bg.mapper;

import com.bg.entity.NetMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ctp
 * @date 2022/12/4 8:46
 */
public interface NetMenuMapper {
    List<NetMenu> getAllNetMenu();
    boolean insertNetMenu(NetMenu netMenu);
    boolean deleteNetMenuById(@Param("netMenuId") Long netMenuId);
}
