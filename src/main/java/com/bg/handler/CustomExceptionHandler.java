package com.bg.handler;

import com.bg.util.ComRet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ctp
 * @date 2022/11/22 18:21
 */
@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    /**
     * 异常响应
     * @return ComRet
     */
    @ExceptionHandler(value = {Exception.class,CustomException.class})
    @ResponseBody
    public ComRet handler(Throwable ex){
        StackTraceElement stackTraceElement = ex.getStackTrace()[0];  // 异常栈收个元素
        log.error("path: " + stackTraceElement.getClassName());     // 路径
        log.error("line: " + stackTraceElement.getLineNumber());    // 行数
        log.error("msg: " + ex.getMessage());       // 信息
        log.error("stack trace: " + ex.toString()); // 堆栈信息
        ex.printStackTrace();
        return ComRet.fail(ex.getMessage());
    }
}
