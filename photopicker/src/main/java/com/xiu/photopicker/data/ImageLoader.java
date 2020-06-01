package com.xiu.photopicker.data;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;

import com.xiu.common.utils.StringUtil;
import com.xiu.datalib.image.Image;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

public class ImageLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "ImageLoader";

    private static volatile ImageLoader instance = null;

    private static final Object LOCK = new Object();

    private Context context;

    private ImageLoaderCallback callback;

    private static final int LOAD_ID = 0x0010;


    private ImageLoader() {
    }

    public static ImageLoader getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }

    public void startLoad(Context context, String path, ImageLoaderCallback callback) {
        this.context = context;
        this.callback = callback;
        Bundle bundle = new Bundle();
        bundle.putString("path", path);
        LoaderManager
                .getInstance((AppCompatActivity) context)
                .initLoader(LOAD_ID, bundle, this);
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String path = args.getString("path");
        String selection = null;
        if (StringUtil.isNotEmpty(path)) {
            selection = MediaStore.Images.Media.DATA + " like %" + path + "%";
        }
        return new CursorLoader(context
                , MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                , new String[]{MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.DATA}
                , selection
                , null
                , MediaStore.Images.Media.DATE_ADDED);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (callback == null) {
            return;
        }
        if (data != null && data.getCount() > 0) {
            List<Image> list = new ArrayList<>();
            Image image;
            while (data.moveToNext()) {
                image = new Image();
                image.setName(data.getString(data.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)));
                image.setPath(data.getString(data.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
                list.add(image);
            }
            callback.onLoaded(list);
        } else {
            callback.onLoadEmpty();
        }
        callback = null;
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

}
