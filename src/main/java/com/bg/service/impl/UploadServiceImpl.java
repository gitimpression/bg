package com.bg.service.impl;

import com.bg.config.KeysProperties;
import com.bg.service.UploadService;
import com.bg.util.ComRet;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传服务
 * @author ctp
 * @date 2022/11/27 8:49
 */
@Service
public class UploadServiceImpl implements UploadService {
    /**
     * 上传文件
     * @param file file
     * @return return
     */
    @Override
    public ComRet upload(MultipartFile file, HttpSession session) {
        // 从上下文获取上传位置
        String realPath = session.getServletContext().getRealPath("upload" + File.separator + "img");
        // 判断目录是否存在
        File f = new File(realPath);
        if (!f.exists()){
            if (!f.mkdirs()){
                // 创建目录失败
                return ComRet.fail("上传失败，创建目录失败");
            }
        }
        String originalFilename = file.getOriginalFilename();
        // 准备就绪，给文件重新命名     demo/xxx-xxx-xxx.jpg
        if (originalFilename == null)
            return ComRet.fail("文件上传失败");
        String fileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String finalPath = realPath + File.separator + fileName;

        // 上传
        try {
            file.transferTo(new File(finalPath));
            return ComRet.ok("上传成功").add("fileName", fileName).add("finalPath", finalPath);
        }catch (Exception e){
            e.printStackTrace();
            return ComRet.fail("上传失败").add("originalFilename",originalFilename);
        }
    }
}
