package com.xiu.skillup.activity.file_list;

import androidx.annotation.NonNull;

import android.os.Bundle;

import com.xiu.skillup.presenter.file_list.ImageFileListPresenter;

public class ImageListActivity extends BaseFileListActivity<ImageFileListPresenter> {

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        setTitle("图片列表");
    }

    @NonNull
    @Override
    public ImageFileListPresenter createPresenter() {
        return new ImageFileListPresenter();
    }

    @Override
    public void onItemClick(int id, int position, String data) {

    }
}
