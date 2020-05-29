package com.xiu.photopicker.mvp_view;

import com.xiu.ui.mvp.MvpView;

import java.util.List;

public interface PhotoPickerView extends MvpView {

    void onLoadImage();

    void onLoadedImage(List list);

}
