package com.xiu.skillup.activity;

import androidx.annotation.NonNull;

import android.os.Bundle;

import com.xiu.skillup.R;
import com.xiu.skillup.presenter.SplashPresenter;
import com.xiu.skillup.view.SplashView;
import com.xiu.ui.mvp.MvpActivity;

public class SplashActivity extends MvpActivity<SplashView,
        SplashPresenter> implements SplashView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getPresenter().countDown();
    }

    @Override
    public void updateCountdownView(String text) {
        findTextViewById(R.id.tv_countdown).setTextSafe(text);
    }

    @Override
    public void intentToMain() {
        MainActivity.newInstance(getContext());
        finish();
    }

    @NonNull
    @Override
    public SplashPresenter createPresenter() {
        return new SplashPresenter();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getPresenter().stopCountDown();
    }
}
