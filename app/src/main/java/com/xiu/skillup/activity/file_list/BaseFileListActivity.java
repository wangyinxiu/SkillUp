package com.xiu.skillup.activity.file_list;

import android.os.Bundle;

import com.xiu.common.utils.ListUtil;
import com.xiu.common.utils.LogUtil;
import com.xiu.skillup.R;
import com.xiu.skillup.adapter.MainAdapter;
import com.xiu.skillup.mvp_view.FileListView;
import com.xiu.skillup.presenter.file_list.FileListPresenter;
import com.xiu.ui.mvp.MvpActivity;
import com.xiu.ui.view.recycler.XRecyclerAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseFileListActivity<P extends FileListPresenter<FileListView>> extends MvpActivity<FileListView, P>
        implements FileListView, XRecyclerAdapter.OnItemClickLister<String> {

    private static final String TAG = BaseFileListActivity.class.getSimpleName();

    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MainAdapter(getContext());
        adapter.setOnItemClickLister(this);
        recyclerView.setAdapter(adapter);
        adapter.addData(getPresenter().getFileList());
    }

    @Override
    protected void onStart() {
        super.onStart();
        getPresenter().startDataLoader();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getPresenter().stopDataLoader();
    }

    @Override
    public void onShowLoading() {
        LogUtil.i(TAG, "onShowLoading");
    }

    @Override
    public void onDataLoaded(List<String> data) {
        LogUtil.i(TAG, "onDataLoaded data size == " + data.size());
        adapter.clear();
        adapter.addData(data);

    }

    @Override
    public void onDismissLoading() {
        LogUtil.i(TAG, "onDismissLoading");
    }


    @Override
    public void onItemClick(int id, int position, String data) {

    }
}
