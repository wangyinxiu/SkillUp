package com.xiu.photopicker.activity;

import androidx.annotation.NonNull;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xiu.common.CommonConfig;
import com.xiu.common.utils.LogUtil;
import com.xiu.photopicker.R;
import com.xiu.photopicker.mvp_view.PhotoPickerView;
import com.xiu.photopicker.presenter.PhotoPickerPresenter;
import com.xiu.ui.base.BaseActivity;
import com.xiu.ui.mvp.MvpActivity;

import java.util.List;

public class PhotoPickerActivity extends MvpActivity<PhotoPickerView,
        PhotoPickerPresenter> implements PhotoPickerView {

    private static final String TAG = "PhotoPickerActivity";

    public static final int REQUEST_PHOTO_PICKER = CommonConfig.REQUEST.REQUEST_PHOTO_PICKER;

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void newInstance(final BaseActivity activity) {
        new RxPermissions(activity).request(PERMISSIONS).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean permission) throws Exception {
                LogUtil.i(TAG,"permission == "+ permission);
                if(permission){
                    Intent intent = new Intent(activity, PhotoPickerActivity.class);
                    activity.startActivityForResult(intent, REQUEST_PHOTO_PICKER);
                }
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_picker);

    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.loadImage(this);
    }

    @Override
    public void onLoadImage() {

    }

    @Override
    public void onLoadedImage(List list) {

    }

    @NonNull
    @Override
    public PhotoPickerPresenter createPresenter() {
        return new PhotoPickerPresenter();
    }
}
