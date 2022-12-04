package com.bg.service;

import com.bg.entity.NetMenu;

import java.util.List;

/**
 * @author ctp
 * @date 2022/12/4 8:44
 */
public interface NetMenuService {
    List<NetMenu> getAllNetMenu();
    boolean insertNetMenu(NetMenu netMenu);
    boolean deleteNetMenuById(Long netMenuId);
}
