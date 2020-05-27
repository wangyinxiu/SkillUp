package com.xiu.ui.mvp.delegate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.xiu.ui.mvp.MvpPresenter;
import com.xiu.ui.mvp.MvpView;

import androidx.annotation.Nullable;



public interface FragmentMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {


  public void onCreate(Bundle saved);


  public void onDestroy();


  public void onViewCreated(View view, @Nullable Bundle savedInstanceState);


  public void onDestroyView();


  public void onPause();


  public void onResume();


  public void onStart();


  public void onStop();


  public void onActivityCreated(Bundle savedInstanceState);


  public void onAttach(Activity activity);


  public void onDetach();


  public void onSaveInstanceState(Bundle outState);
}
