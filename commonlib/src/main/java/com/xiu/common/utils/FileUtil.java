package com.xiu.common.utils;
import android.os.Environment;
import java.io.File;
import java.io.IOException;

public class FileUtil {

    private static final String TAG = "FileUtil";

    private static final String PACKAGE_NAME = "com.xiu.skillup";

    public static void createSkillUpRootDir(boolean permission){
        createDir(getCacheRootPath(permission));
        createDir(getImageRootPath(permission));
        createDir(getVideoRootPath(permission));
    }

    public static String getImageRootPath(){
        return getImageRootPath(true);
    }

    public static String getImageRootPath(boolean permission){
        return getRootPath(permission)+File.separator+"image";
    }

    public static String getVideoRootPath(){
        return getVideoRootPath(true);
    }

    public static String getVideoRootPath(boolean permission){
        return getRootPath(permission)+File.separator+"video";
    }

    public static String getCacheRootPath(){
        return getCacheRootPath(true);
    }

    public static String getCacheRootPath(boolean permission){
        return getRootPath(permission)+File.separator+"cache";
    }

    public static void createDir(String path){
        File file = new File(path);
        if(!file.exists()){
            boolean result = file.mkdirs();
            LogUtil.i(TAG,"create dir result == "+result+" ,path == "+path);
        }
    }


    public static String getRootPath(boolean permission){
        String path = null;
        try {
            path = null;
            if(isExternalStorageMounted() && permission){
                path =
                        Environment.getExternalStorageDirectory().getCanonicalPath()+File.separator+ PACKAGE_NAME;
            }else {
                path =
                        Environment.getDataDirectory().getCanonicalPath()+File.separator+PACKAGE_NAME;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static boolean isExternalStorageMounted(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean exists(String path){
        File file = new File(path);
        return file.exists();
    }

    public static boolean exists(String dir,String name){
        return exists(dir+File.separator+name);
    }

    public static File createNewImageFile(boolean permission){
        String path =
                getImageRootPath(permission)+File.separator+"Image_"+System.currentTimeMillis()+
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
