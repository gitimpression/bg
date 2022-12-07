package com.bg.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 存放key值
 * @author ctp
 * @date 2022/11/26 15:42
 */
public class KeysProperties {
    public static final String TOKEN_KEY = "u_t";               // 前端存放token
    public static final String USER_INFO_KEY = "u_i";           // 前端用户信息
    public static final String USER_PASSWORD_KEY = "password";  // 前端用户密码

    public static final String TOKEN_USER_ID_KEY = "userId";    // 用户id
    public static final String TOKEN_USERNAME_KEY = "username"; // 用户名
    public static final String TOKEN_ROLE_ID_KEY = "roleId";    // 用户角色id


    public static final String USER_HEADER_IMG_PREFIX = "http://localhost:8081/upload/img/";

    public static final String SQL_USER_LOG_METHOD_LOGIN = "com.bg.controller.UserController.login";
    public static final String USER_NOTICE_OPERATION_PERMISSION_KEY = "notice_operation";
    public static final String USER_NET_OPERATION_PERMISSION_KEY = "net_operation";
    public static final String USER_FEEDBACK_OPERATION_PERMISSION_KEY = "feedback_operation";


    public static final List<String> USER_LOG_METHOD_FILTER_ARRAY = Arrays.asList("get","update","delete","insert","login","logout");

}
