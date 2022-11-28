package com.bg.handler;

/**
 * @author ctp
 * @date 2022/11/27 22:24
 */
public class CustomException extends RuntimeException{
    public CustomException(){
    }
    public CustomException(String msg){
        super(msg);
    }
}
