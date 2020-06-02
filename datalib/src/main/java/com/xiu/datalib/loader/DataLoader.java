package com.xiu.datalib.loader;


import android.nfc.Tag;

import com.xiu.common.utils.ArrayUtil;
import com.xiu.common.utils.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DataLoader {

    private static final String TAG = "DataLoader";

    private static DataLoader instance = null;
    private static final Object LOCK = new Object();
    private int count = 0;
    private List<OnDataLoaderListener> listeners = null;
    private Stack<File> dirs = null;
    private List<String> images = null;
    private List<String> videos = null;
    private List<String> medias = null;
    private ExecutorService executors = null;
    private boolean isLoaded = false;
    private boolean isLoading = false;

    private DataLoader() {
        listeners = new ArrayList<>();
        dirs = new Stack<>();
        images = new ArrayList<>();
        videos = new ArrayList<>();
        medias = new ArrayList<>();
        executors = Executors.newFixedThreadPool(5);
    }

    public static DataLoader getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = new DataLoader();
                }
            }
        }
        return instance;
    }

    public void addOnDataLoaderListener(OnDataLoaderListener listener) {
        listeners.add(listener);
    }

    public void removeOnDataLoaderListener(OnDataLoaderListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public void startLoader(File root) {
        if (isLoading) {
            LogUtil.i(TAG, "start loader but is loading");
            return;
        }
        if (isLoaded) {
            LogUtil.i(TAG, "start loader but is already loaded");
            onComplete();
            return;
        }
        isLoading = true;
        dirs.add(root);
        startScan();
    }

    public void stopLoader() {

    }

    private void startScan() {
        Observable.create((ObservableOnSubscribe<File>) emitter -> {
            count++;
            File root = dirs.remove(0);
            File[] list = root.listFiles();
            if (ArrayUtil.isFileArrayNotEmpty(list)) {
                for (File file : list) {
                    emitter.onNext(file);
                }
            }
            emitter.onComplete();
        })
                .subscribeOn(Schedulers.from(executors))
                .observeOn(Schedulers.io())
                .subscribe(new DisposableObserver<File>() {
                    @Override
                    public void onNext(File file) {
                        if (file.isDirectory()) {
                            dirs.add(file);
                            startScan();
                        } else {
                            String path = file.getAbsolutePath();
                            String name =
                                    path.substring(path.lastIndexOf(File.separator) + 1).toLowerCase();
                            dealVideoFile(path, name);
                            dealImageFile(path, name);
                            dealMediaFile(path, name);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (--count == 0) {
                            DataLoader.this.onComplete();
                            isLoaded = true;
                            isLoading = false;
                        }
                    }
                });
    }

    private void onComplete() {
        for (OnDataLoaderListener listener : listeners) {
            listener.onLoadComplete();
        }
    }


    private void dealMediaFile(String path, String name) {
        if (name.endsWith(".mp3") || name.endsWith(".3gp")) {
            medias.add(path);
        }
    }

    private void dealVideoFile(String path, String name) {
        if (name.endsWith(".mp4") || name.endsWith(".wma")) {
            videos.add(path);
        }
    }

    private void dealImageFile(String path, String name) {
        if (name.endsWith(".jpg")
                || name.endsWith(".jpeg")
                || name.endsWith(".png")) {
            images.add(path);
        }
    }

    public List<String> getMediaData() {
        return medias;
    }

    public List<String> getVideoData() {
        return videos;
    }

    public List<String> getImageData() {
        return images;
    }

    public void clear() {
        dirs.clear();
        dirs = null;
        listeners.clear();
        listeners = null;
        images.clear();
        images = null;
        videos.clear();
        videos = null;
        medias.clear();
        medias = null;
        executors = null;
        instance = null;
    }


}
