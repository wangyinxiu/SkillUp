package com.xiu.common.utils;

public class ArrayUtil {

    public static boolean isIntArrayEmpty(int[] array){
        return array == null || array.length == 0;
    }

    public static boolean isIntArrayNotEmpty(int[] array){
        return !isIntArrayEmpty(array);
    }
}
