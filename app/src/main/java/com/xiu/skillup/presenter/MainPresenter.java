package com.xiu.skillup.presenter;

import android.content.Context;
import android.content.Intent;

import com.xiu.datalib.image.Image;
import com.xiu.photopicker.activity.AlbumPickerActivity;
import com.xiu.skillup.mvp_view.MainView;
import com.xiu.ui.mvp.MvpBasePresenter;

import java.util.ArrayList;

public class MainPresenter extends MvpBasePresenter<MainView> {

    public void onActivityResult(Context context, int requestCode,
                                 int resultCode, Intent data) {
        if (AlbumPickerActivity.hasData(requestCode, resultCode)) {
            ArrayList<Image> imageArrayList =
                    AlbumPickerActivity.getImageData(data);
            String path = "";
            for (Image image : imageArrayList) {
                path += image.getPath() + ",";
            }
            getView().onShowToast(path);
        }

    }

}
