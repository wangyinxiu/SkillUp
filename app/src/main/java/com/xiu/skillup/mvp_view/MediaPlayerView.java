package com.xiu.skillup.mvp_view;

import com.xiu.datalib.common.MediaInfo;
import com.xiu.ui.mvp.MvpView;

import java.util.List;

public interface MediaPlayerView extends MvpView {

    void onItButton();

    void onInitGallery(int position,List<MediaInfo> data);

    void onInitName(int position,List<MediaInfo> data);

    void onPageChanged(int position);

    void onChangePlayButtonText(String text);
}
