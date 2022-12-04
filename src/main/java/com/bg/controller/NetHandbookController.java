package com.bg.controller;

import com.bg.anno.Log;
import com.bg.config.KeysProperties;
import com.bg.entity.NetHandbook;
import com.bg.service.NetHandbookService;
import com.bg.util.ComRet;
import com.bg.util.JwtUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author ctp
 * @date 2022/12/4 11:45
 */
@RestController
@RequestMapping("/netHandbook")
public class NetHandbookController {
    @Resource
    private NetHandbookService netHandbookService;

    @Log("获取网络服务 故障手册列表")
    @GetMapping
    public ComRet getAllNetHandbook(){
        List<NetHandbook> netHandbookList = netHandbookService.getAllNetHandbook();
        return ComRet.ok().add("list", netHandbookList);
    }

    @Log("增加 网络服务 故障手册 记录")
    @PutMapping
    public ComRet insertNetHandbook(@RequestHeader Map<String,String> headers,
                                    @RequestBody NetHandbook netHandbook){

        if(netHandbook == null){
            return ComRet.fail("参数有误");
        }
        long userId = Long.parseLong(JwtUtil.claims(headers, KeysProperties.TOKEN_USER_ID_KEY));
        netHandbook.setUserId(userId);
        boolean b = netHandbookService.insertNetHandbook(netHandbook);
        if (b){
            return ComRet.ok("添加成功");
        }else{
            return ComRet.fail("添加失败");
        }
    }

    @Log("删除 网络服务 故障手册 记录")
    @DeleteMapping
    public ComRet deleteNetHandbook(@RequestBody Map<String, String> body){
        if(body == null || body.get("netHandbookId") == null){
            return ComRet.fail("参数有误");
        }
        boolean b = netHandbookService.deleteNetHandbook(Long.parseLong(body.get("netHandbookId")));
        if (b){
            return ComRet.ok("删除成功");
        }else{
            return ComRet.fail("删除失败");
        }
    }
}
