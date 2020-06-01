package com.xiu.photopicker.mvp_view;

import com.xiu.datalib.image.Image;
import com.xiu.ui.mvp.MvpView;

import java.util.List;

public interface AlbumPickerView extends MvpView {

    void onStartLoad();

    void onLoaded(List<Image> list);

    void onLoadEmpty();
}
