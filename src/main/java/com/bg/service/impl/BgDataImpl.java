package com.bg.service.impl;

import com.bg.entity.BgData;
import com.bg.mapper.BgDataMapper;
import com.bg.service.BgDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

/**
 * 数据字典服务接口实现类
 * @author ctp
 * @date 2022/11/27 1:27
 */
@Service
@Transactional
public class BgDataImpl implements BgDataService {
    @Resource
    private BgDataMapper bgDataMapper;
    @Override
    public List<BgData> getDataByConditions(BgData bgData) {
        return bgDataMapper.getDataByConditions(bgData);
    }

    @Override
    public List<BgData> getAllData() {
        return bgDataMapper.getAllData();
    }
}
