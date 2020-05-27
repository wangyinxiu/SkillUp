package com.xiu.common.utils;

public class StringUtil {

    public static boolean isEmpty(String str){
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    public static int getLength(String str){
        int len = 0;
        if(str != null){
            len = str.length();
        }
        return len;
    }

}
