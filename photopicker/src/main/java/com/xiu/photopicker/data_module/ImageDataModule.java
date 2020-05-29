package com.xiu.photopicker.data_module;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;

import com.xiu.common.utils.LogUtil;
import com.xiu.ui.base.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

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


    public void loadImage(BaseActivity activity) {
        LoaderManager loaderManager = activity.getSupportLoaderManager();
        loaderManager.initLoader(LOADER_ALL, null, new ImageLoader(activity));
    }

    private class ImageLoader implements LoaderManager.LoaderCallbacks<Cursor> {

        private BaseActivity activity;

        public ImageLoader(BaseActivity activity) {
            this.activity = activity;
        }

        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
            CursorLoader cursorLoader = null;
            //扫描所有图片
            if (id == LOADER_ALL) {
                cursorLoader = new CursorLoader(activity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, null, null, IMAGE_PROJECTION[6] + " DESC");
            }
            //扫描某个图片文件夹
            if (id == LOADER_CATEGORY) {
                cursorLoader = new CursorLoader(activity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION, IMAGE_PROJECTION[1] + " like '%" + args.getString("path") + "%'", null, IMAGE_PROJECTION[6] + " DESC");
            }

            return cursorLoader;
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
            LogUtil.i(TAG, "cursor count == " + data.getCount());

            while (data.moveToNext()) {
                String imageName = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                String imagePath = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                LogUtil.i(TAG, "image name == " + imageName);
                LogUtil.i(TAG, "image path == " + imagePath);
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        }
    }


}
