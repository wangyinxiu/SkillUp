package com.xiu.skillup.presenter.file_list;

import com.xiu.datalib.MediaFileUtil;
import com.xiu.datalib.common.MediaInfo;
import com.xiu.datalib.loader.DataLoader;
import com.xiu.skillup.mvp_view.FileListView;

import java.util.ArrayList;
import java.util.List;

public class MediaFileListPresenter extends FileListPresenter<MediaInfo,
        FileListView> {

    @Override
    public List<MediaInfo> getFileList() {
        List<MediaInfo> list = new ArrayList<>();
        for (String path:DataLoader.getInstance().getMediaData()) {
            list.add(MediaFileUtil.disassmenbleFile(path));
        }
        return list;
    }
}
