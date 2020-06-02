package com.xiu.skillup.activity.file_list;

import androidx.annotation.NonNull;

import android.os.Bundle;

import com.xiu.skillup.presenter.file_list.VideoFileListPresenter;

public class VideoListActivity extends BaseFileListActivity<VideoFileListPresenter> {

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        setTitle("视频列表");
    }

    @NonNull
    @Override
    public VideoFileListPresenter createPresenter() {
        return new VideoFileListPresenter();
    }

    @Override
    public void onItemClick(int id, int position, String data) {

    }
}
