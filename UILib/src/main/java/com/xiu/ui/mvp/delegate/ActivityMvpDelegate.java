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
