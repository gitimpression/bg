package com.bg.service.impl;

import com.bg.entity.NetHandbook;
import com.bg.mapper.NetHandbookMapper;
import com.bg.service.NetHandbookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ctp
 * @date 2022/12/4 11:44
 */
@Service
public class NetHandbookServiceImpl implements NetHandbookService {
    @Resource
    private NetHandbookMapper netHandbookMapper;

    @Override
    public List<NetHandbook> getAllNetHandbook() {
        return netHandbookMapper.getAllNetHandbook();
    }

    @Override
    public boolean insertNetHandbook(NetHandbook netHandbook) {
        return netHandbookMapper.insertNetHandbook(netHandbook);
    }

    @Override
    public boolean deleteNetHandbook(Long netHandbookId) {
        return netHandbookMapper.deleteNetHandbook(netHandbookId);
    }
}
