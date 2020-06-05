package com.xiu.skillup.activity.file_list;

import com.xiu.skillup.adapter.SingleButtonAdapter;
import com.xiu.skillup.presenter.file_list.VideoFileListPresenter;

import androidx.annotation.NonNull;

public class VideoListActivity extends BaseFileListActivity<String,
        SingleButtonAdapter,
        VideoFileListPresenter> {

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
    public SingleButtonAdapter createAdapter() {
        return new SingleButtonAdapter(getContext());
    }

    @Override
    public void onItemClick(int position, String data) {

    }
}
