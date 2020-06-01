package com.xiu.photopicker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xiu.common.CommonConfig;
import com.xiu.photopicker.R;
import com.xiu.photopicker.mvp_view.PhotoPickerView;
import com.xiu.photopicker.presenter.PhotoPickerPresenter;
import com.xiu.ui.base.BaseActivity;
import com.xiu.ui.mvp.MvpActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PhotoPickerActivity extends MvpActivity<PhotoPickerView,
        PhotoPickerPresenter> implements PhotoPickerView, View.OnClickListener {

    private static final int REQUEST_CAMERA =
            CommonConfig.REQUEST.REQUEST_CAMERA;

    public static void newInstance(BaseActivity activity) {
        Intent intent = new Intent(activity, PhotoPickerActivity.class);
        activity.startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carama);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        setTitle("图片选取");
        setOnClickListener(this::onClick,
                R.id.btn_camera_only,
                R.id.btn_camera_crop,
                R.id.btn_album_multiple,
                R.id.btn_album_single,
                R.id.btn_system_album);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_camera_only){
            getPresenter().startCamera(this);
        }else if(id == R.id.btn_camera_crop){
            getPresenter().startCrop(this);
        }else if(id == R.id.btn_album_multiple){
            getPresenter().startAlbumMultiple(this);
        }else if(id == R.id.btn_album_single){
            getPresenter().startAlbumSingle(this);
        }else if(id == R.id.btn_system_album){
            getPresenter().startSystemAlbum(this);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getPresenter().onActivityResult(this,requestCode,resultCode,data);
    }

    @NonNull
    @Override
    public PhotoPickerPresenter createPresenter() {
        return new PhotoPickerPresenter();
    }
}
