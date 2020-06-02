package com.xiu.photopicker.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Camera;
import android.net.Uri;
import android.provider.MediaStore;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.xiu.common.CommonConfig;
import com.xiu.common.utils.FileUtil;
import com.xiu.common.utils.LogUtil;
import com.xiu.photopicker.activity.AlbumPickerActivity;
import com.xiu.photopicker.mvp_view.PhotoPickerView;
import com.xiu.ui.base.BaseActivity;
import com.xiu.ui.mvp.MvpBasePresenter;

import java.io.File;

import androidx.core.content.FileProvider;


public class PhotoPickerPresenter extends MvpBasePresenter<PhotoPickerView> {

    private static final String TAG = "PhotoPickerPresenter";

    private Uri cameraUri;

    public void startCamera(BaseActivity activity) {
        new RxPermissions(activity)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    cameraUri = FileProvider.getUriForFile(activity
                            , activity.getPackageName() + ".fileprovider"
                            , FileUtil.createNewImageFile(permission));
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                    activity.startActivityForResult(intent, CommonConfig.REQUEST.REQUEST_CAMERA);
                });


    }

    public void startCrop(BaseActivity activity) {
//        ,Uri uri,int width,int height
//            ,int aspectX ,int aspectY
//        if(width == 0 || height == 0){
//            width = height = 0;
//        }
//
//        if(aspectX == 0 || aspectY == 0){
//            aspectX = aspectY = 1;
//        }
//
//        Intent intent = new Intent();
//        intent.setAction(Camera.ACTION_CROP);

    }

    public void startAlbumSingle(BaseActivity activity) {
        AlbumPickerActivity.newSingleInstnce(activity);
    }

    public void startAlbumMultiple(BaseActivity activity) {
        AlbumPickerActivity.newInstance(activity);
    }

    public void startSystemAlbum(BaseActivity activity) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, CommonConfig.REQUEST.REQUEST_SYSTEM_ALBUM);
    }

    public void onActivityResult(BaseActivity activity, int requestCode,
                                 int resultCode, Intent data) {
        LogUtil.i(TAG, "requestCode == " + requestCode + " , resultCode == " + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CommonConfig.REQUEST.REQUEST_CAMERA) {
                disassembleCamera(activity, data);
            }
        }
    }

    private void disassembleCamera(BaseActivity activity, Intent data) {
        LogUtil.i(TAG, "disassembleCamera");
//        File file = new File(cameraUri);
//        Uri uri = cameraUri;
//        cameraUri.getPath();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(cameraUri);
        activity.sendBroadcast(intent);

    }

}
