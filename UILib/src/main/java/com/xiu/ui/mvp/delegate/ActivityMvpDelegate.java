package com.xiu.ui.mvp.delegate;

import android.os.Bundle;

import com.xiu.ui.mvp.MvpPresenter;
import com.xiu.ui.mvp.MvpView;

public interface ActivityMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {


  void onCreate(Bundle bundle);


  void onDestroy();


  void onPause();


  void onResume();


  void onStart();


  void onStop();


  void onRestart();


  void onContentChanged();


  void onSaveInstanceState(Bundle outState);


  void onPostCreate(Bundle savedInstanceState);
}
