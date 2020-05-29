package com.xiu.skillup;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;


import com.xiu.common.utils.LogUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class App extends Application implements Application.ActivityLifecycleCallbacks,
        Application.OnProvideAssistDataListener {

    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        LogUtil.i(TAG,"onCreate");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LogUtil.i(TAG,"onTerminate");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.i(TAG,"onConfigurationChanged");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogUtil.i(TAG,"onLowMemory");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtil.i(TAG,"onTrimMemory");
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
//        LogUtil.i(TAG,
//                "onActivityCreated activity == " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
//        LogUtil.i(TAG,
//                "onActivityStarted activity == " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
//        LogUtil.i(TAG,
//                "onActivityResumed activity == " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
//        LogUtil.i(TAG,
//                "onActivityPaused activity == " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
//        LogUtil.i(TAG,
//                "onActivityStopped activity == " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
//        LogUtil.i(TAG,
//                "onActivitySaveInstanceState activity == " + activity.getClass().getSimpleName());
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
//        LogUtil.i(TAG,
//                "onActivityDestroyed activity == " + activity.getClass().getSimpleName());
    }

    @Override
    public void onProvideAssistData(Activity activity, Bundle bundle) {
//        LogUtil.i(TAG,
//                "onProvideAssistData activity == " + activity.getClass().getSimpleName());
    }
}
