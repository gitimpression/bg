package com.bg.util;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求统一响应类
 * @author ctp
 * @date 2022/11/22 4:06
 */
@Data
public class ComRet{
    private Integer code;
    private String  msg;
    private Map<String, Object> data = new HashMap<>();
    private static Integer DEFAULT_OK_CODE = 200;
    private static Integer DEFAULT_FAIL_CODE = 500;
    private static String DEFAULT_OK_MESSAGE = "ok";
    private static String DEFAULT_FAIL_MESSAGE = "error";
    public ComRet(){
    }
    public ComRet(Integer code){
        this.code = code;
    }
    public ComRet(Integer code, String msg){
        this(code);
        this.msg = msg;
    }

    public ComRet(Integer code, String msg, Map<String, Object> data){
        this(code,msg);
        this.data = data;
    }
    public static ComRet ok(){
        return new ComRet(DEFAULT_OK_CODE, DEFAULT_OK_MESSAGE , null);
    }

    public static ComRet ok(String key, Object value){
        HashMap<String, Object> map = new HashMap<>();
        map.put(key, value);
        return new ComRet(DEFAULT_OK_CODE, DEFAULT_OK_MESSAGE , map);
    }

    public static ComRet ok(Integer code){
        return new ComRet(code, DEFAULT_OK_MESSAGE , null);
    }
    public static ComRet ok(String msg){
        return new ComRet(DEFAULT_OK_CODE, msg , null);
    }
    public static ComRet ok(Map<String, Object> data){
        return new ComRet(DEFAULT_OK_CODE, DEFAULT_OK_MESSAGE , data);
    }

    public static ComRet ok(Integer code, String msg){
        return new ComRet(code, msg , null);
    }
    public static ComRet ok(Integer code, Map<String, Object> data){
        return new ComRet(code, DEFAULT_OK_MESSAGE , data);
    }
    public static ComRet ok(String msg, Map<String, Object> data){
        return new ComRet(DEFAULT_OK_CODE, msg , data);
    }

    public static ComRet ok(Integer code, String msg, Map<String, Object> data){
        return new ComRet(code, msg , data);
    }

    public static ComRet fail(){
        return new ComRet(DEFAULT_FAIL_CODE, DEFAULT_FAIL_MESSAGE , null);
    }

    public static ComRet fail(Integer code){
        return new ComRet(code, DEFAULT_FAIL_MESSAGE , null);
    }
    public static ComRet fail(String msg){
        return new ComRet(DEFAULT_FAIL_CODE, msg , null);
    }
    public static ComRet fail(Map<String, Object> data){
        return new ComRet(DEFAULT_FAIL_CODE, DEFAULT_FAIL_MESSAGE , data);
    }

    public static ComRet fail(Integer code, String msg){
        return new ComRet(code, msg , null);
    }
    public static ComRet fail(Integer code, Map<String, Object> data){
        return new ComRet(code, DEFAULT_FAIL_MESSAGE , data);
    }
    public static ComRet fail(String msg, Map<String, Object> data){
        return new ComRet(DEFAULT_FAIL_CODE, msg , data);
    }

    public static ComRet fail(Integer code, String msg, Map<String, Object> data){
        return new ComRet(code, msg , data);
    }

    public static ComRet fail(String key, Object value){
        HashMap<String, Object> map = new HashMap<>();
        map.put(key, value);
        return new ComRet(DEFAULT_FAIL_CODE, DEFAULT_FAIL_MESSAGE , map);
    }

    public ComRet add(String key, Object value){
        if (this.data == null){
            this.data = new HashMap<>();
        }
        this.data.put(key, value);
        return this;
    }
}
