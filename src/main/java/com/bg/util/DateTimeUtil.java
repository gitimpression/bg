package com.bg.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 常用格式日期时间工具类
 * @author ctp
 * @date 2022/11/30 0:19
 */
public class DateTimeUtil {

    /**
     *
     * @return yyyy-MM-dd
     */
    public static String getDate(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now(ZoneId.systemDefault()));
    }

    /**
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDataTime(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now(ZoneId.systemDefault()));
    }
}
