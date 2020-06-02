package com.xiu.skillup.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.xiu.common.utils.LogUtil;
import com.xiu.datalib.loader.DataLoader;
import com.xiu.media.MediaLoader;
import com.xiu.photopicker.activity.PhotoPickerActivity;
import com.xiu.skillup.R;
import com.xiu.skillup.adapter.MainAdapter;
import com.xiu.skillup.mvp_view.MainView;
import com.xiu.skillup.presenter.MainPresenter;
import com.xiu.ui.mvp.MvpActivity;
import com.xiu.ui.view.recycler.XRecyclerAdapter;

public class MainActivity extends MvpActivity<MainView, MainPresenter> implements MainView,
        XRecyclerAdapter.OnItemClickLister<String> {

    private static final String ITEM_PHOTO_PICK = "图片选择";
    private static final String ITEM_TEST_TEMP = "临时测试";
    private static final String ITEM_LOAD_IMAGE = "加载图片列表";
    private static final String ITEM_LOAD_VIDEO = "加载视频列表";
    private static final String ITEM_LOAD_MEDIA = "加载音乐列表";
    private MainAdapter adapter = null;

    public static void newInstance(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        adapter = new MainAdapter(getContext());
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.addData(ITEM_LOAD_IMAGE);
        adapter.addData(ITEM_LOAD_VIDEO);
        adapter.addData(ITEM_LOAD_MEDIA);
        adapter.setOnItemClickLister(this);
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
}
