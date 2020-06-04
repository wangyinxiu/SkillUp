package com.xiu.media;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.xiu.common.utils.LogUtil;

public class MediaService extends Service {

    private MediaBinder mediaBinder;

    public MediaService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaBinder = new MediaBinder(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.i(getClass().getSimpleName(),"bind == "+ intent.getDataString());
        return mediaBinder;
    }
}
