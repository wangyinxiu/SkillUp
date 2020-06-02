package com.xiu.media;

import android.Manifest;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
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
import io.reactivex.functions.Consumer;
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

        new RxPermissions(activity).requestEach(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Permission>() {
            @Override
            public void accept(Permission permission) throws Exception {
                if(permission.granted){
                    startScan();
                }else {
                    LogUtil.i(TAG,"not permission");
                }
            }
        });
    }

    private void startScan(){
        LogUtil.i(TAG,"startScan");
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                File root =Environment.getExternalStorageDirectory().getAbsoluteFile();
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
        LogUtil.i(TAG,"dir == "+ dir.getAbsolutePath());
        File[] fileList = dir.getAbsoluteFile().listFiles();
        LogUtil.i(TAG,"root name == "+ dir.getName()+",size == "+ fileList.length);
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
