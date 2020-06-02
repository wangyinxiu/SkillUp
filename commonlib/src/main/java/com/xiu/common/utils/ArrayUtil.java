package com.xiu.common.utils;

import java.io.File;

public class ArrayUtil {

    public static boolean isIntArrayEmpty(int[] array){
        return array == null || array.length == 0;
    }

    public static boolean isIntArrayNotEmpty(int[] array){
        return !isIntArrayEmpty(array);
    }

    public static boolean isFileArrayEmpty(File[] array){
        return array == null || array.length == 0;
    }

    public static boolean isFileArrayNotEmpty(File[] array){
        return !isFileArrayEmpty(array);
    }
}
