package com.xiu.ui.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Cache;
import com.squareup.picasso.Picasso;
import com.xiu.common.utils.BitmapUtil;
import com.xiu.common.utils.LogUtil;

import java.io.File;

public class PicassoUtil {

    private static final String TAG = "PicassoUtil";

    private static final int DEFAULT_HOLDER_RES_ID = 0;
    private static final int DEFAULT_ERROR_RES_ID = 1;

    public static void init(Context context){
        LogUtil.i(TAG,"init picasso");
        Picasso picasso = new Picasso.Builder(context)
                .loggingEnabled(true)
                .build();
        Picasso.setSingletonInstance(picasso);
    }


    public static void load(String path, ImageView imageView){
        Picasso.get().load(path)
//                .placeholder(DEFAULT_HOLDER_RES_ID)
//                .error(DEFAULT_ERROR_RES_ID)
                .into(imageView);
    }

    public static void load(String path,int holder,int error,
                                      ImageView imageView){
        Picasso.get().load(path)
                .placeholder(holder)
                .error(error)
                .into(imageView);
    }

    public static void loadNoPlaceHolder(String path,ImageView imageView){
        Picasso.get().load(path).noPlaceholder().into(imageView);
    }

    public static void load(int resId,ImageView imageView){
        Picasso.get().load(resId).into(imageView);
    }

    public static void load(File file,ImageView imageView){
        Picasso.get().load(file).into(imageView);
    }

}
