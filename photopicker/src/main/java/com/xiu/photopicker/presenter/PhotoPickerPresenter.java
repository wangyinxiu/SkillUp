package com.xiu.photopicker.presenter;

import android.content.Context;

import com.xiu.photopicker.data_module.ImageDataModule;
import com.xiu.photopicker.data_module.ImageDataModuleCallback;
import com.xiu.photopicker.mvp_view.PhotoPickerView;
import com.xiu.ui.base.BaseActivity;
import com.xiu.ui.mvp.MvpBasePresenter;

public class PhotoPickerPresenter extends MvpBasePresenter<PhotoPickerView> implements ImageDataModuleCallback {

    public void loadImage(BaseActivity context) {
        ImageDataModule dataModule = ImageDataModule.getInstance();
        dataModule.addImageDataModuleCallback(this);
        dataModule.loadImage(context);
    }

    @Override
    public void onImageLoaded() {
        ImageDataModule.getInstance().removeImageDataModuleCallback(this);
    }
}
