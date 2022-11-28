package com.bg.service;

import com.bg.util.ComRet;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * 文件上传接口
 * @author ctp
 * @date 2022/11/27 8:49
 */
public interface UploadService {
    /**
     * 上传文件
     * @param file 文件
     * @return 上传完成的文件名
     */
    ComRet upload(MultipartFile file,HttpSession session);
}
