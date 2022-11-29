package com.bg.util;

import java.time.LocalDate;

/**
 * 用户有关的工具类，如校验用户名
 * @author ctp
 * @date 2022/11/29 13:28
 */
public class UserUtil {


    /**
     * 校验用户名是否合法
     * @return true -->> 合法
     */
    public static boolean checkUsername(String username){
        String regex = "^[a-zA-Z0-9_-]{4,16}$";// 前端 /^[a-zA-Z0-9_-]{4,16}$/ 4到16位（字母，数字，下划线，减号）
        return username.matches(regex);
    }

    /**
     * 校验用户密码是否合法
     * @return true -->> 合法
     */
    public static boolean checkPassword(String password){
        String regex = "^[a-zA-Z0-9]{6,20}$";// 前端 /^[a-zA-Z0-9]{6,20}$/ //6到20位（字母，数字）
        return password.matches(regex);
    }

    /**
     * 校验用户邮箱是否合法
     * @return true -->> 合法
     */
    public static boolean checkEmail(String email){
        String regex = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";// 前端 /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/
        return email.matches(regex);
    }

    /**
     * 校验用户生日是否合法
     * @return true -->> 合法
     */
    public static boolean checkBirthday(String birthday){
        String regex = "^([1-9]\\d{3}-)(([0]{0,1}[1-9]-)|([1][0-2]-))(([0-3]{0,1}[0-9]))$";
        boolean matches = birthday.matches(regex);
        if (!matches){  // 格式校验不通过
            return false;
        }
        String[] date = birthday.split("-");

        int i = date.length; // 2022-02-02
        if (i != 3){
            return false;
        }

        // 选择的年月日
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);
        if (year < 1900 || month < 1 || day < 1){
            return false;
        }
        // 当前年月日
        LocalDate now = LocalDate.now();
        int nowYear = now.getYear();
        int nowMonth = now.getMonthValue();
        int nowDay = now.getDayOfMonth();

        if(year < nowYear){  // 今年之前
            return true;
        }else if (year == nowYear) {  // 今年，这个月之前
            if(month < nowMonth){
                return true;
            }else if (month == nowMonth) {  //  今年这个月，今天之前
                return day <= nowDay;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

}
