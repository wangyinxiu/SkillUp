package com.xiu.ui.mvp.delegate;

import com.xiu.ui.mvp.MvpPresenter;
import com.xiu.ui.mvp.MvpView;



public interface ViewGroupMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {


  void onAttachedToWindow();


  void onDetachedFromWindow();

}
