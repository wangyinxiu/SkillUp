package com.xiu.ui.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.xiu.ui.view.XTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    private SparseArray<View> viewCache;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(android.R.id.content).setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(viewCache != null){
            viewCache.clear();
            viewCache = null;
        }
    }

    @Override
    public <T extends View> T findViewById(int id) {
        if(viewCache == null){
            viewCache = new SparseArray<>();
        }
        View view = viewCache.get(id);
        if(view == null){
            view = super.findViewById(id);
            viewCache.put(id,view);
        }
        return (T) view;
    }

    public XTextView findTextViewById(int id){
        return findViewById(id);
    }

    public ImageView findImageViewById(int id){
        return findViewById(id);
    }

    public Context getContext(){
        return this;
    }

}