package com.xiu.media;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.xiu.common.utils.FileUtil;
import com.xiu.common.utils.LogUtil;
import com.xiu.ui.base.BaseActivity;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MediaLoader {

    private static final String TAG = "MediaLoader";

    private static final String[] PROJECTION = new String[]{
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DESCRIPTION,
            MediaStore.Images.Media.TITLE,
            MediaStore.Images.Media.SIZE
    };

    private Context context;

    public void startLoader(BaseActivity activity) {
        LogUtil.i(TAG,"startLoader");
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                File root =
                        Environment.getExternalStorageDirectory();
                scanDir(root,emitter);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void scanDir(File dir,ObservableEmitter<String> emitter) {
        File[] fileList = dir.listFiles();
        for (File file : fileList) {
            if (dir.isDirectory()) {
                LogUtil.i(TAG,"file name == "+ file.getName());
                LogUtil.i(TAG,"file path == "+ file.getAbsolutePath());
                scanDir(file,emitter);
            } else {
                String fileName = file.getPath();
                if (fileName.endsWith(".mp3")) {

                }else {

                }
                LogUtil.i(TAG, "fileName == " + fileName);
                emitter.onNext(fileName);
            }
        }
    }


}
