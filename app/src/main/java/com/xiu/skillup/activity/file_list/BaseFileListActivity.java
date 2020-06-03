package com.xiu.skillup.activity.file_list;

import com.xiu.common.utils.LogUtil;
import com.xiu.skillup.mvp_view.FileListView;
import com.xiu.skillup.presenter.file_list.FileListPresenter;
import com.xiu.ui.base.recycler.BaseRecyclerActivity;
import com.xiu.ui.view.recycler.XRecyclerAdapter;

import java.util.List;

public  abstract class BaseFileListActivity
        <T,
        A extends XRecyclerAdapter<T>,
        P extends FileListPresenter<T,FileListView>
                >
        extends BaseRecyclerActivity<T,A,FileListView, P>
        implements FileListView {

    private static final String TAG = BaseFileListActivity.class.getSimpleName();


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
    public void onDataLoaded(List data) {
        LogUtil.i(getClass().getSimpleName(),"data size == "+ data.size());
        getAdapter().addData(data);
    }

    @Override
    public void onDismissLoading() {
        LogUtil.i(TAG, "onDismissLoading");
    }

    @Override
    public boolean enableRefreshFooter() {
        return false;
    }

    @Override
    public boolean enableRefreshHeader() {
        return false;
    }
}
