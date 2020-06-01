package com.xiu.photopicker.presenter;

import com.xiu.datalib.image.Image;
import com.xiu.photopicker.data.ImageDataModule;
import com.xiu.photopicker.data.ImageDataModuleCallback;
import com.xiu.photopicker.mvp_view.AlbumPickerView;
import com.xiu.ui.base.BaseActivity;
import com.xiu.ui.mvp.MvpBasePresenter;

import java.util.List;

public class AlbumPickerPresenter extends MvpBasePresenter<AlbumPickerView> implements ImageDataModuleCallback {

    public void loadImage(BaseActivity context) {
        getView().onStartLoad();
        ImageDataModule dataModule = ImageDataModule.getInstance();
        dataModule.addImageDataModuleCallback(this);
        dataModule.loadImage(context);
    }

    @Override
    public void onImageLoaded(List<Image> data) {
        if(isViewAttached()){
            getView().onLoaded(data);
        }
        ImageDataModule.getInstance().removeImageDataModuleCallback(this);
    }

    @Override
    public void onImageLoadEmpty() {
        if(isViewAttached()){
            getView().onLoadEmpty();
        }
        ImageDataModule.getInstance().removeImageDataModuleCallback(this);
    }
}
