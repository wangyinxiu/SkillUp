package com.xiu.skillup.presenter.file_list;

import android.Manifest;
import android.content.Context;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xiu.datalib.MediaFileUtil;
import com.xiu.datalib.common.MediaInfo;
import com.xiu.datalib.loader.DataLoader;
import com.xiu.skillup.activity.MediaPlayerActivity;
import com.xiu.skillup.mvp_view.FileListView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MediaFileListPresenter extends FileListPresenter<MediaInfo,
        FileListView> {

    @Override
    public List<MediaInfo> getFileList() {
        List<MediaInfo> list = new ArrayList<>();
        for (String path : DataLoader.getInstance().getMediaData()) {
            list.add(MediaFileUtil.disassmenbleFile(path));
        }
        return list;
    }

    public void start(AppCompatActivity activity, int position,
                      List<MediaInfo> data) {
        new RxPermissions(activity)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission) {
                        MediaPlayerActivity.newInstance(activity, position, data);
                    }
                });
    }

}
