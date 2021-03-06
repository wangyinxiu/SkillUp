package com.xiu.skillup.presenter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xiu.datalib.loader.DataLoader;
import com.xiu.skillup.activity.file_list.ImageListActivity;
import com.xiu.skillup.activity.file_list.MediaListActivity;
import com.xiu.skillup.activity.file_list.VideoListActivity;
import com.xiu.skillup.mvp_view.MainView;
import com.xiu.ui.base.BaseActivity;
import com.xiu.ui.mvp.MvpBasePresenter;

public class MainPresenter extends MvpBasePresenter<MainView> {

    public void intent2MediaListActivity(Context context){
        Intent intent = new Intent(context, MediaListActivity.class);
        context.startActivity(intent);
    }

    public void intent2VideoListActivity(Context context){
        Intent intent = new Intent(context, VideoListActivity.class);
        context.startActivity(intent);
    }

    public void intent2ImageListActivity(Context context){
        Intent intent = new Intent(context, ImageListActivity.class);
        context.startActivity(intent);
    }

    public void startDataLoader(BaseActivity activity) {
        new RxPermissions(activity).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(permission -> {
            if (permission) {
                DataLoader.getInstance().startLoader(Environment.getExternalStorageDirectory());
            } else {
                DataLoader.getInstance().startLoader(Environment.getDataDirectory());
            }
        });
    }


}
