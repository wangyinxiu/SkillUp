package com.xiu.ui.view.recycler.gallery;

import android.content.Context;
import android.util.AttributeSet;

import com.xiu.common.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class GalleryRecyclerView extends RecyclerView {

    private static final float FLING_SCALE_DOWN_FACTOR = 0.5f; // 减速因子
    private static final int FLING_MAX_VELOCITY = 2000; // 最大顺时滑动速度
    private boolean enableLimitVelocity = true; // 最大顺时滑动速度
    private List<OnPageChangeListener> mOnPageChangeListeners;

    public GalleryRecyclerView(Context context) {
        super(context);
    }

    public GalleryRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isEnableLimitVelocity() {
        return enableLimitVelocity;
    }

    public void setEnableLimitVelocity(boolean enableLimitVelocity) {
        this.enableLimitVelocity = enableLimitVelocity;
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        if (enableLimitVelocity) {
            velocityX = solveVelocity(velocityX);
            velocityY = solveVelocity(velocityY);
        }
        return super.fling(velocityX, velocityY);
    }

    private int solveVelocity(int velocity) {
        if (velocity > 0) {
            return Math.min(velocity, FLING_MAX_VELOCITY);
        } else {
            return Math.max(velocity, -FLING_MAX_VELOCITY);
        }
    }

    public void addOnPageChangeListener(OnPageChangeListener listener) {
        if (mOnPageChangeListeners == null) {
            mOnPageChangeListeners = new ArrayList<>();
        }
        mOnPageChangeListeners.add(listener);
    }

    public void removeOnPageChangeListener(OnPageChangeListener listener) {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.remove(listener);
        }
    }

    public void clearOnPageChangeListeners() {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.clear();
        }
    }


    public interface OnPageChangeListener {
        void onPageSelected(int position);
    }

    public void dispatchOnPageSelected(int position) {
        if (mOnPageChangeListeners != null) {
            for (OnPageChangeListener listener : mOnPageChangeListeners) {
                if (listener != null) {
//
//                    int realPosition =
//                            ((GalleryBaseAdapter) getAdapter()).getRealPosition(position);
//                    LogUtil.i(getClass().getSimpleName(),
//                            "position == "+position+" , real == "+ realPosition);
                    listener.onPageSelected(position);
                }
            }
        }
    }

}
