package com.xiu.skillup.view;

import com.xiu.ui.mvp.MvpView;

public interface SplashView extends MvpView {

    void updateCountdownView(String text);

    void intentToMain();

}
