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
