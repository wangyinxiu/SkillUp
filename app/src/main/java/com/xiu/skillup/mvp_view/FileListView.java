package com.xiu.skillup.mvp_view;

import com.xiu.ui.mvp.MvpView;

import java.util.List;

public interface FileListView extends MvpView {

    void onShowLoading();

    void onDataLoaded(List<String> data);

    void onDismissLoading();

}
