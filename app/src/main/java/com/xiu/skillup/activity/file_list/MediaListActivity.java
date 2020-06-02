package com.xiu.skillup.activity.file_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.xiu.skillup.R;
import com.xiu.skillup.presenter.file_list.MediaFileListPresenter;

public class MediaListActivity extends BaseFileListActivity<MediaFileListPresenter> {

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
    public void onItemClick(int id, int position, String data) {

    }
}
