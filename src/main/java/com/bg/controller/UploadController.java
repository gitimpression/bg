package com.bg.controller;

import com.bg.anno.Log;
import com.bg.config.KeysProperties;
import com.bg.service.UploadService;
import com.bg.util.ComRet;
import com.bg.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author ctp
 * @date 2022/11/27 8:46
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Resource
    private UploadService uploadService;

    /**
     * 上传图片
     * @param file file
     * @return return
     */
    @Log("用户上传图片")
    @PostMapping("/img")
    public ComRet upload(MultipartFile file, HttpSession session) {
        if (file == null){
            return ComRet.fail("获取目标文件失败");
        }

        // 判断文件名
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")){
            return ComRet.fail("获取文件后缀失败");
        }

        // 后缀 如 .jpg 含点
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (!".jpg".equals(suffix.toLowerCase()) && !".png".equals(suffix.toLowerCase()) && !".jpeg".equals(suffix.toLowerCase())){
            return ComRet.fail("可以上传的图片文件格式:jpg、jpeg、png，后缀不区分大小写");
        }

        return uploadService.upload(file, session);
    }
}
