package com.xiu.skillup.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xiu.skillup.R;
import com.xiu.ui.mvp.MvpActivity;
import com.xiu.ui.mvp.MvpNullObjectBasePresenter;
import com.xiu.ui.mvp.MvpView;

public class MainActivity extends MvpActivity<MvpView,
        MvpNullObjectBasePresenter<MvpView>> implements MvpView {

    public static void newInstance(Context context){
        Intent intent = new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @NonNull
    @Override
    public MvpNullObjectBasePresenter<MvpView> createPresenter() {
        return new MvpNullObjectBasePresenter<>();
    }
}
