package com.csdl.smartplacenew.util;

/**
 * 验证类
 * @author kenshine
 * @create 2020-05-18 9:12
 **/
public class Validator {


    public static boolean isNull(String str){
        if(str==null||"".equals(str.trim())){
            return true;
        }
        return false;
    }

    public static boolean isNotNull(String str){
        return !isNull(str);
    }

}
