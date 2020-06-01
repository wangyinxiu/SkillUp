package com.xiu.photopicker.data;

import com.xiu.datalib.image.Image;

import java.util.List;

public interface ImageLoaderCallback {

    void onLoaded(List<Image> data);

    void onLoadEmpty();
}
