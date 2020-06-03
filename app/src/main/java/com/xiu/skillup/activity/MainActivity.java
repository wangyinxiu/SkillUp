package com.xiu.skillup.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.xiu.common.utils.LogUtil;
import com.xiu.photopicker.activity.PhotoPickerActivity;
import com.xiu.skillup.adapter.SingleButtonAdapter;
import com.xiu.skillup.mvp_view.MainView;
import com.xiu.skillup.presenter.MainPresenter;
import com.xiu.ui.base.recycler.BaseRecyclerActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MainActivity extends
        BaseRecyclerActivity<String,SingleButtonAdapter,MainView, MainPresenter>
        implements MainView {

    private static final String ITEM_PHOTO_PICK = "图片选择";
    private static final String ITEM_TEST_TEMP = "临时测试";
    private static final String ITEM_LOAD_IMAGE = "加载图片列表";
    private static final String ITEM_LOAD_VIDEO = "加载视频列表";
    private static final String ITEM_LOAD_MEDIA = "加载音乐列表";

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        SingleButtonAdapter adapter = getAdapter();
        adapter.setOnItemClickLister(this);
        adapter.addData(ITEM_LOAD_IMAGE);
        adapter.addData(ITEM_LOAD_VIDEO);
        adapter.addData(ITEM_LOAD_MEDIA);
        setTitle("功能项");
    }

    @Override
    public void onItemClick(int id, int position, String data) {
        switch (data) {
            case ITEM_LOAD_IMAGE:
                getPresenter().intent2ImageListActivity(getContext());
                break;
            case ITEM_LOAD_VIDEO:
                getPresenter().intent2VideoListActivity(getContext());
                break;
            case ITEM_LOAD_MEDIA:
                getPresenter().intent2MediaListActivity(getContext());
                break;
            case ITEM_TEST_TEMP:
                presenter.startDataLoader(this);
                break;
            case ITEM_PHOTO_PICK:
                PhotoPickerActivity.newInstance(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void onShowToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getPresenter().onActivityResult(getContext(), requestCode, resultCode, data);
    }

    @Override
    public SingleButtonAdapter createAdapter() {
        return new SingleButtonAdapter(getContext());
    }




}
