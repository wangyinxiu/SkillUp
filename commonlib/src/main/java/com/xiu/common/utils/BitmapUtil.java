package com.xiu.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtil {

    public static final int SAVE_SUCCESS = 0;
    public static final int SAVE_EXISTS = 1;
    public static final int SAVE_EXCEPTION = 2;

    /**
     *
     * @param bitmap
     * @param name
     * @return true正常执行 false已经存在目标路径图片，放弃保存
     */
    public static int saveBitmapToImageRoot(String name,Bitmap bitmap){
        File target =
                new File(FileUtil.getImageRootPath()+File.separator+name);
        if(target.exists()){
           return SAVE_EXISTS;
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(target);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return SAVE_EXCEPTION;
        }finally {
            try {
                if(fileOutputStream!=null){
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return SAVE_SUCCESS;
    }

    public static Bitmap readBitmapFromImageRoot(String name){
        String path = FileUtil.getImageRootPath() + File.separator + name;
        Bitmap bitmap = null;
        if(FileUtil.exists(path)){
            bitmap = BitmapFactory.decodeFile(path);
        }
        return bitmap;
    }


}
