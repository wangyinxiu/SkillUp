package com.xiu.skillup.activity.file_list;

import com.xiu.datalib.common.MediaInfo;
import com.xiu.skillup.adapter.MediaInfoAdapter;
import com.xiu.skillup.presenter.file_list.MediaFileListPresenter;

import androidx.annotation.NonNull;

public class MediaListActivity extends BaseFileListActivity
        <MediaInfo, MediaInfoAdapter, MediaFileListPresenter> {


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        setTitle("音乐列表");
    }

    @NonNull
    @Override
    public MediaFileListPresenter createPresenter() {
        return new MediaFileListPresenter();
    }

    @Override
    public MediaInfoAdapter createAdapter() {
        return new MediaInfoAdapter(getContext());
    }

    @Override
    public void onItemClick(int id, int position, MediaInfo data) {
        getPresenter().start(this,position,getAdapter().getData());
    }

}
