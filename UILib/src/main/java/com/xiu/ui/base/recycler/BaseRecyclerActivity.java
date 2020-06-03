package com.xiu.ui.base.recycler;

import android.os.Bundle;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xiu.ui.R;
import com.xiu.ui.mvp.MvpActivity;
import com.xiu.ui.mvp.MvpBasePresenter;
import com.xiu.ui.mvp.MvpView;
import com.xiu.ui.view.recycler.XRecyclerAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerActivity
        <T,A extends XRecyclerAdapter<T>, V extends MvpView,
                P extends MvpBasePresenter<V>> extends MvpActivity<V,P>
        implements OnRefreshLoadMoreListener, XRecyclerAdapter.OnItemClickLister<T> {

    private A adapter;
    private SmartRefreshLayout refreshLayout;
    private int page = 0;
    private static final int DEFAULT_REFRESH_TIME_OUT = 10*1000;

    public abstract A createAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_all);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        refreshLayout = findViewById(R.id.refresh_layout);
        refreshLayout.setEnableRefresh(enableRefreshHeader());
        refreshLayout.setEnableLoadMore(enableRefreshFooter());
        refreshLayout.setOnRefreshLoadMoreListener(this);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(createLayoutManager());
        recyclerView.setAdapter(adapter = createAdapter());
        adapter.setOnItemClickLister(this);
    }



    public RecyclerView.LayoutManager createLayoutManager(){
        return new LinearLayoutManager(getContext());
    }

    public boolean enableRefreshHeader(){
        return true;
    }

    public boolean enableRefreshFooter(){
        return true;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        onRefresh(++page);
        refreshLayout.finishLoadMore(refreshTimeout());
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        onRefresh(page = 0);
        refreshLayout.finishRefresh(refreshTimeout());
    }

    /**
     * 刷新完成调用
     */
    public void refreshFinish(){

        if(page == 0){
            refreshLayout.finishRefresh();
        }else {
            refreshLayout.finishLoadMore();
        }
    }

    /**
     * 展示加载动画 按需加载
     * @param page current page
     */
    public void showLoading(int page){

    }

    /**
     * 复写此方法刷新页面
     * @param page
     */
    public  void onRefresh(int page){
        showLoading(page);
    }


    /**
     * 刷新超时时间
     * @return 超时时间
     */
    public int refreshTimeout(){
        return DEFAULT_REFRESH_TIME_OUT;
    }

    public A getAdapter(){
        return adapter;
    }

}
