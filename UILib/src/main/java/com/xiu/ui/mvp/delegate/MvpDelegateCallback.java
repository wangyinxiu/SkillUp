package com.xiu.ui.mvp.delegate;


import com.xiu.ui.mvp.MvpPresenter;
import com.xiu.ui.mvp.MvpView;


public interface MvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>> {


  P createPresenter();


  P getPresenter();


  void setPresenter(P presenter);


  V getMvpView();


  boolean isRetainingInstance();
}
