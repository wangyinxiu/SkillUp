package com.xiu.skillup.presenter.file_list;

import android.os.Environment;

import com.xiu.datalib.loader.DataLoader;
import com.xiu.datalib.loader.OnDataLoaderListener;
import com.xiu.skillup.mvp_view.FileListView;
import com.xiu.ui.mvp.MvpBasePresenter;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public abstract class FileListPresenter<T,V extends FileListView> extends MvpBasePresenter<V> implements OnDataLoaderListener {

    public abstract List<T> getFileList();


    public void startDataLoader(){
        DataLoader.getInstance().addOnDataLoaderListener(this);
        DataLoader.getInstance().startLoader(Environment.getExternalStorageDirectory());
    }

    public void stopDataLoader(){
        DataLoader.getInstance().removeOnDataLoaderListener(this);
    }

    @Override
    public void onStartLoader() {
        if(isViewAttached()){
            Observable
                    .just(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integer -> {
                        if(isViewAttached()){
                            getView().onShowLoading();
                        }
                    });

        }

    }

    @Override
    public void onLoadComplete() {
        if(isViewAttached()){
            Observable
                    .just(1)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integer -> {
                        if(isViewAttached()){
                            getView().onDataLoaded(getFileList());
                            DataLoader.getInstance().removeOnDataLoaderListener(this);
                            getView().onDismissLoading();
                        }
                    });

        }
    }
}
