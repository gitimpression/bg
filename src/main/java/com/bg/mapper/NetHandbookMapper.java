package com.bg.mapper;

import com.bg.entity.NetHandbook;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ctp
 * @date 2022/12/4 11:38
 */
public interface NetHandbookMapper {
    List<NetHandbook> getAllNetHandbook();
    boolean insertNetHandbook(NetHandbook netHandbook);
    boolean deleteNetHandbook(@Param("netHandbookId") Long netHandbookId);
}
