package com.bg.mapper;

import com.bg.entity.BgData;

import java.util.List;

/**
 * @author ctp
 * @date 2022/11/27 1:17
 */
public interface BgDataMapper {
    /**
     * 以 Data 不为空的字段进行查询
     * @param data data
     * @return Data
     */
    List<BgData> getDataByConditions(BgData data);
    List<BgData> getAllData();
}
