/*
 * Copyright 2015 Hannes Dorfmann.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
