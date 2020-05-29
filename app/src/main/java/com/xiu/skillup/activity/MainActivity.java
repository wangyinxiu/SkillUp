package com.xiu.skillup.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xiu.photopicker.activity.PhotoPickerActivity;
import com.xiu.skillup.R;
import com.xiu.skillup.adapter.MainAdapter;
import com.xiu.skillup.mvp_view.MainView;
import com.xiu.skillup.presenter.MainPresenter;
import com.xiu.ui.mvp.MvpActivity;
import com.xiu.ui.mvp.MvpNullObjectBasePresenter;
import com.xiu.ui.mvp.MvpView;
import com.xiu.ui.view.recycler.XRecyclerAdapter;

public class MainActivity extends MvpActivity<MainView, MainPresenter> implements MainView,
        XRecyclerAdapter.OnItemClickLister<String> {

    private static final String ITEM_PHOTO_PICK = "图片选择";
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
        adapter.addData(ITEM_PHOTO_PICK);
        adapter.setOnItemClickLister(this);

    }

    @Override
    public void onItemClick(int id, int position, String data) {
        switch (data) {
            case ITEM_PHOTO_PICK:
                PhotoPickerActivity.newInstance(this);
                break;
            default:
                break;
        }
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
