package com.bg.handler;

import com.bg.util.ComRet;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ctp
 * @date 2022/11/22 18:21
 */
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * 异常响应
     * @return ComRet
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public ComRet handler(Throwable ex){
        return ComRet.fail(ex.getMessage());
    }
}
