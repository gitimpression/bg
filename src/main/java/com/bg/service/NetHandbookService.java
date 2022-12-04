package com.bg.service;

import com.bg.entity.NetHandbook;

import java.util.List;

/**
 * @author ctp
 * @date 2022/12/4 11:44
 */
public interface NetHandbookService {
    List<NetHandbook> getAllNetHandbook();
    boolean insertNetHandbook(NetHandbook netHandbook);
    boolean deleteNetHandbook(Long netHandbookId);
}
