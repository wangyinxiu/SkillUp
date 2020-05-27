package com.xiu.ui.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.xiu.ui.mvp.delegate.FragmentMvpDelegate;
import com.xiu.ui.mvp.delegate.FragmentMvpDelegateImpl;
import com.xiu.ui.mvp.delegate.MvpDelegateCallback;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public abstract class MvpFragment<V extends MvpView,
        P extends MvpPresenter<V>> extends Fragment implements MvpDelegateCallback<V, P>, MvpView {

    private static final String TAG = "MvpFragment";
    protected AppCompatActivity mContext = null;
    protected FragmentMvpDelegate<V, P> mvpDelegate;

    /**
     * The presenter for this view. Will be instantiated with {@link #createPresenter()}
     */
    protected P presenter;


    /**
     * Creates a new presenter getInstance, if needed. Will reuse the previous presenter getInstance if
     * {@link #setRetainInstance(boolean)} is set to true. This method will be called from
     * {@link #onViewCreated(View, Bundle)}
     */
    @Override
    public abstract P createPresenter();

    /**
     * * Get the mvp delegate. This is internally used for creating presenter, attaching and
     * detaching view from presenter.
     *
     * <p>
     * <b>Please note that only one getInstance of mvp delegate should be used per fragment
     * getInstance</b>.
     * </p>
     *
     * <p>
     * Only override this method if you really know what you are doing.
     * </p>
     *
     * @return {@link FragmentMvpDelegateImpl}
     */
    @NonNull
    protected FragmentMvpDelegate<V, P> getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new FragmentMvpDelegateImpl(this);
        }

        return mvpDelegate;
    }

    @NonNull
    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(@NonNull P presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean isRetainingInstance() {
        return getRetainInstance();
    }

    @NonNull
    @Override
    public V getMvpView() {
        return (V) this;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMvpDelegate().onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getMvpDelegate().onDestroyView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvpDelegate().onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getMvpDelegate().onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMvpDelegate().onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        getMvpDelegate().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMvpDelegate().onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getMvpDelegate().onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getMvpDelegate().onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getMvpDelegate().onSaveInstanceState(outState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (AppCompatActivity) context;
    }


}

