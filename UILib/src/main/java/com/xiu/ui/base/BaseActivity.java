package com.xiu.ui.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiu.common.utils.LogUtil;
import com.xiu.ui.R;
import com.xiu.ui.utils.UiUtil;
import com.xiu.ui.view.XTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    private SparseArray<View> viewCache;

    protected Toolbar toolbar;
    protected ActionBar actionBar;
    private TextView titleView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(android.R.id.content).setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        setUpActionBar();
    }

    private void setUpActionBar() {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            toolbar.setNavigationIcon(R.mipmap.icon_left);
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
            titleView = findViewById(R.id.toolbar_title);
            if (titleView != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
            lp.topMargin = UiUtil.getStatusBarHeight(getContext());
            toolbar.setLayoutParams(lp);
        }
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (titleView != null) {
            titleView.setText(title);
//            titleView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//                @Override
//                public boolean onPreDraw() {
////                    titleView.getViewTreeObserver().removeOnPreDrawListener(this::onPreDraw);
////                    int offset =
////                            getResources().getDisplayMetrics().widthPixels-titleView.getWidth()/2;
////                    ViewCompat.setX(titleView,offset);
//                    return false;
//                }
//            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewCache != null) {
            viewCache.clear();
            viewCache = null;
        }
    }

    @Override
    public <T extends View> T findViewById(int id) {
        if (viewCache == null) {
            viewCache = new SparseArray<>();
        }
        View view = viewCache.get(id);
        if (view == null) {
            view = super.findViewById(id);
            viewCache.put(id, view);
        }
        return (T) view;
    }

    public XTextView findTextViewById(int id) {
        return findViewById(id);
    }

    public ImageView findImageViewById(int id) {
        return findViewById(id);
    }

    public Context getContext() {
        return this;
    }

    public void setOnClickListener(View.OnClickListener onClickListener,
                                   int... ids) {
        for (int id : ids) {
            findViewById(id).setOnClickListener(onClickListener);
        }
    }

}