package com.xiu.skillup.presenter;

import com.xiu.common.utils.LogUtil;
import com.xiu.skillup.mvp_view.SplashView;
import com.xiu.ui.mvp.MvpBasePresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class SplashPresenter extends MvpBasePresenter<SplashView> {

    private static final String TAG = "SplashPresenter";

    private Disposable disposable;
    private static final int count = 5;

    public void countDown(){
        Observable
                .interval(1000,TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        LogUtil.i(TAG,"onNext == "+aLong);

                        if(isViewAttached()){
                            int value = count - aLong.intValue();
                            getView().updateCountdownView(value+"s");
                            if(value == 0){
                                stopCountDown();
                                getView().intentToMain();
                            }
                        }else {
                            stopCountDown();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void stopCountDown(){
        if(disposable != null && !disposable.isDisposed()){
            disposable.dispose();
            disposable = null;
        }
    }

}
