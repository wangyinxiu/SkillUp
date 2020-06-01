package com.xiu.photopicker.data;

import com.xiu.datalib.image.Image;

import java.util.List;

public interface ImageDataModuleCallback {

    void onImageLoaded(List<Image> data);

    void onImageLoadEmpty();

}
