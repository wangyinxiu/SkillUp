package com.xiu.ui.mvp;

import androidx.annotation.NonNull;

public class MvpNullObjectBasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V view;

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @NonNull
    protected V getView() {
        if (view == null) {
            throw new NullPointerException("MvpView reference is null. Have you called attachView()?");
        }
        return view;
    }

    @Override
    public void detachView(boolean retainInstance) {
        if (view != null) {
            //noinspection unchecked
            if (0 == (view.getClass().getGenericInterfaces()).length){
                return;
            }
            Class<V> viewClass = (Class<V>) view.getClass().getGenericInterfaces()[0];
            view = NoOp.of(viewClass);
        }
    }
}