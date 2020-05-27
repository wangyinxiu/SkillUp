package com.xiu.ui.utils;

import android.util.Log;

import com.xiu.ui.BuildConfig;

public class LogUtil {

    public void i(String tag,String msg){
//        if(){
            Log.i(BuildConfig.TAG,tag+" --> "+msg);
//        }
    }


}
