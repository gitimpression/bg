package com.bg.controller;

import com.bg.anno.Log;
import com.bg.entity.BgData;
import com.bg.service.BgDataService;
import com.bg.util.ComRet;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * 数据字典查询
 * @author ctp
 * @date 2022/11/27 1:29
 */
@RestController
public class BgDataController {

    @Resource
    private BgDataService bgDataService;

    @PostMapping("/data")
    public ComRet getDataByConditions(@RequestBody BgData bgData){
        List<BgData> datas = bgDataService.getDataByConditions(bgData);
        return ComRet.ok().add("data", datas);
    }

    @Log("增加数据字典")
    @PostMapping("/datas")
    public ComRet getAllData(){
        List<BgData> datas = bgDataService.getAllData();
        return ComRet.ok().add("data", datas);
    }
}
