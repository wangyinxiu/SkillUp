package com.xiu.photopicker;

import android.content.Intent;
import android.provider.MediaStore;

import com.xiu.common.CommonConfig;
import com.xiu.ui.base.BaseActivity;

public class CameraUtil {


    public static void getBySystemAlbum(BaseActivity activity){
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, CommonConfig.REQUEST.REQUEST_SYSTEM_ALBUM);
    }


}
