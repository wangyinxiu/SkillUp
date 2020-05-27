package com.xiu.ui.mvp.delegate;


import com.xiu.ui.mvp.MvpPresenter;
import com.xiu.ui.mvp.MvpView;


class MvpInternalDelegate<V extends MvpView, P extends MvpPresenter<V>> {

  protected MvpDelegateCallback<V, P> delegateCallback;

  MvpInternalDelegate(MvpDelegateCallback<V, P> delegateCallback) {

    if (delegateCallback == null) {
      throw new NullPointerException("MvpDelegateCallback is null!");
    }

    this.delegateCallback = delegateCallback;
  }


  void createPresenter() {

    P presenter = delegateCallback.getPresenter();
    if (presenter == null) {
      presenter = delegateCallback.createPresenter();
    }
    if (presenter == null) {
      throw new NullPointerException("Presenter is null! Do you return null in createPresenter()?");
    }

    delegateCallback.setPresenter(presenter);
  }


  void attachView() {
    getPresenter().attachView(delegateCallback.getMvpView());
  } 


  void detachView() {
    getPresenter().detachView(delegateCallback.isRetainingInstance());
  }

  private P getPresenter() {
    P presenter = delegateCallback.getPresenter();
    if (presenter == null) {
      throw new NullPointerException("Presenter returned from getPresenter() is null");
    }
    return presenter;
  }
}
