package com.xiu.common.utils;

import android.os.Environment;

import java.io.File;

public class FileUtil {

    public static String getImageRootDir(){
        return getRootDir()+File.separator+"image";
    }


    public static String getRootDir(){
        String path = null;
        File sdCardFile = getSDCardFile();
        if(sdCardFile == null){
            path = Environment.getDataDirectory().getAbsolutePath();
        }else {
            path = sdCardFile.getAbsolutePath();        }
        return path;
    }


    public static File getSDCardFile(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir;
    }

    public static boolean exists(String path){
        File file = new File(path);
        return file.exists();
    }

    public static boolean exists(String dir,String name){
        return exists(dir+File.separator+name);
    }




}
