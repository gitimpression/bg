package com.bg.service;

import com.bg.entity.BgData;

import java.util.List;

/**
 * 数据字典接口
 * @author ctp
 * @date 2022/11/27 1:26
 */
public interface BgDataService {
    List<BgData> getDataByConditions(BgData bgData);

    List<BgData> getAllData();
}
