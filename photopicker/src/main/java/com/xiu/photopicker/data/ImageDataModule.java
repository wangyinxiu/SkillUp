package com.xiu.photopicker.data;

import android.content.Context;
import android.provider.MediaStore;

import com.xiu.datalib.image.Image;

import java.util.ArrayList;
import java.util.List;

public class ImageDataModule {

    private static final String TAG = "ImageDataModule";

    public static final int LOADER_ALL = 0;         //加载所有图片
    public static final int LOADER_CATEGORY = 1;    //分类加载图片

    private final String[] IMAGE_PROJECTION = {     //查询图片需要的数据列
            MediaStore.Images.Media.DISPLAY_NAME,   //图片的显示名称  aaa.jpg
            MediaStore.Images.Media.DATA,           //图片的真实路径  /storage/emulated/0/pp/downloader/wallpaper/aaa.jpg
            MediaStore.Images.Media.SIZE,           //图片的大小，long型  132492
            MediaStore.Images.Media.WIDTH,          //图片的宽度，int型  1920
            MediaStore.Images.Media.HEIGHT,         //图片的高度，int型  1080
            MediaStore.Images.Media.MIME_TYPE,      //图片的类型     image/jpeg
            MediaStore.Images.Media.DATE_ADDED};    //图片被添加的时间，long型  1450518608

    private static ImageDataModule instance = null;

    private List<ImageDataModuleCallback> callbacks = null;

    private static final Object LOCK = new Object();

    private ImageDataModule() {
        callbacks = new ArrayList<>();
    }

    public static ImageDataModule getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new ImageDataModule();
                }
            }
        }
        return instance;
    }

    public void addImageDataModuleCallback(ImageDataModuleCallback callback) {
        callbacks.add(callback);
    }

    public void removeImageDataModuleCallback(ImageDataModuleCallback callback) {
        callbacks.remove(callback);
    }


    public void loadImage(Context context) {
        ImageLoader.getInstance().startLoad(context, null, new ImageLoaderCallback() {
            @Override
            public void onLoaded(List<Image> data) {
                for (ImageDataModuleCallback callback : callbacks) {
                    callback.onImageLoaded(data);
                }
            }

            @Override
            public void onLoadEmpty() {
                for (ImageDataModuleCallback callback : callbacks) {
                    callback.onImageLoadEmpty();
                }
            }
        });
    }

}
