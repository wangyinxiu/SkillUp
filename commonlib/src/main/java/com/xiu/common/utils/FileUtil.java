package com.xiu.common.utils;

import android.Manifest;
import android.os.Environment;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.functions.Consumer;

public class FileUtil {

    public static String getImageRootDir(){
        return getRootDirPath()+File.separator+"image";
    }



    public static String getRootDirPath(){
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

    public static File createNewImageFile(){
        File dir = new File(getImageRootDir());
        if(!dir.exists()){
            dir.mkdirs();
        }
        String path = getImageRootDir() +File.separator+"Image_"+System.currentTimeMillis()+
                ".jpg";
        File file = new File(path);
        try {
            boolean result = file.createNewFile();
            if(result){
                return file;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




}
