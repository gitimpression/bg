package com.bg.service.impl;

import com.bg.entity.NetMenu;
import com.bg.mapper.NetMenuMapper;
import com.bg.service.NetMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * "网络服务"接口
 * @author ctp
 * @date 2022/12/4 8:44
 */
@Service
public class NetMenuServiceImpl implements NetMenuService {
    @Resource
    private NetMenuMapper netMenuMapper;

    @Override
    public List<NetMenu> getAllNetMenu() {
        return netMenuMapper.getAllNetMenu();
    }

    @Override
    public boolean insertNetMenu(NetMenu netMenu) {
        return netMenuMapper.insertNetMenu(netMenu);
    }

    @Override
    public boolean deleteNetMenuById(Long netMenuId) {
        return netMenuMapper.deleteNetMenuById(netMenuId);
    }
}
