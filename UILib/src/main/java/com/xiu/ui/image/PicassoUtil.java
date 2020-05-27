package com.xiu.ui.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Cache;
import com.squareup.picasso.Picasso;
import com.xiu.common.utils.BitmapUtil;

import java.io.File;

public class PicassoUtil {

    private static final int DEFAULT_HOLDER_RES_ID = 0;
    private static final int DEFAULT_ERROR_RES_ID = 1;

    public static void init(Context context){
        Picasso picasso = new Picasso.Builder(context.getApplicationContext())
                .memoryCache(new Cache() {
                    @Override
                    public Bitmap get(String key) {
                        return BitmapUtil.readBitmapFromImageRoot(key);
                    }

                    @Override
                    public void set(String key, Bitmap bitmap) {
                        BitmapUtil.saveBitmapToImageRoot(key,bitmap);
                    }

                    @Override
                    public int size() {
                        return 0;
                    }

                    @Override
                    public int maxSize() {
                        return 0;
                    }

                    @Override
                    public void clear() {

                    }

                    @Override
                    public void clearKeyUri(String keyPrefix) {

                    }
                })
                .build();
        Picasso.setSingletonInstance(picasso);
    }


    public static void load(String path, ImageView imageView){
        Picasso.get().load(path)
                .placeholder(DEFAULT_HOLDER_RES_ID)
                .error(DEFAULT_ERROR_RES_ID)
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
