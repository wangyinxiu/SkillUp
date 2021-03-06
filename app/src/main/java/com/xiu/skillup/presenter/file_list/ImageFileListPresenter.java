package com.xiu.skillup.presenter.file_list;

import com.xiu.datalib.common.MediaInfo;
import com.xiu.datalib.loader.DataLoader;
import com.xiu.skillup.mvp_view.FileListView;

import java.util.List;

public class ImageFileListPresenter extends FileListPresenter<String,
        FileListView> {
    @Override
    public List<String> getFileList() {
        return DataLoader.getInstance().getImageData();
    }
}
