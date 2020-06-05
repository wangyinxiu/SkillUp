package com.xiu.ui.view.recycler.gallery;

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver;

import com.xiu.common.utils.LogUtil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


public class GalleryScaleHelper {

    private static final String TAG = "GalleryScaleHelper";

    private GalleryRecyclerView recyclerView;
    private float scale = 0.8f;
    private int pagePadding;
    private int showLeftCardWidth;

    private int cardWidth;
    private int scrollOneCardWidth;
    private int recyclerViewWidth;

    private int firstPosition;
    private int currentOffset;

    private CardLinearSnapHelper snapHelper;
    private int lastPosition;




    public void attachToRecyclerView(final GalleryRecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        recyclerView.addOnScrollListener(onScrollListener);


        GalleryAdapterHelper helper = ((GalleryBaseAdapter) recyclerView.getAdapter()).getGalleryAdapterHelper();
        pagePadding = helper.getPagePadding();
        showLeftCardWidth = helper.getShowLeftCardWidth();

        this.recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);

        snapHelper = new CardLinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    public void setPosition(int position,int size) {
        setPosition(position+Integer.MAX_VALUE/size/2*size,false);
    }

    public void setPosition(int position, boolean smoothScroll) {
        if (smoothScroll) {
            recyclerView.smoothScrollToPosition(position);
        }else {
            scrollToPosition(position);
        }
    }

    private void scrollToPosition(int position) {
        ((LinearLayoutManager) recyclerView.getLayoutManager()).
                scrollToPositionWithOffset(position, dip2px(pagePadding + showLeftCardWidth));
        currentOffset = 0;
        lastPosition = position;
        recyclerView.dispatchOnPageSelected(lastPosition);
        recyclerView.post(() -> onScrolledChangedCallback());
    }

    public void setStartPosition(int firstItemPos, int size) {
        this.firstPosition = Integer.MAX_VALUE/size/2*size+firstItemPos;
    }

    public int getPosition() {
        return recyclerView.getLayoutManager()
                .getPosition(snapHelper.findSnapView(recyclerView.getLayoutManager()));
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setPagePadding(int pagePadding) {
        this.pagePadding = pagePadding;
    }

    public void setShowLeftCardWidth(int showLeftCardWidth) {
        this.showLeftCardWidth = showLeftCardWidth;
    }
    
    private void onScrolledChangedCallback() {
        if (scrollOneCardWidth == 0) {
            return;
        }
        int currentItemPos = getPosition();
        int offset = currentOffset - (currentItemPos - lastPosition) * scrollOneCardWidth;
        float percent = (float) Math.max(Math.abs(offset) * 1.0 / scrollOneCardWidth, 0.0001);
        View leftView = null;
        View currentView;
        View rightView = null;
        if (currentItemPos > 0) {
            leftView = recyclerView.getLayoutManager().findViewByPosition(currentItemPos - 1);
        }
        currentView = recyclerView.getLayoutManager().findViewByPosition(currentItemPos);
        if (currentItemPos < recyclerView.getAdapter().getItemCount() - 1) {
            rightView = recyclerView.getLayoutManager().findViewByPosition(currentItemPos + 1);
        }
        if (leftView != null) {
            leftView.setScaleY((1 - scale) * percent + scale);
        }
        if (currentView != null) {
            currentView.setScaleY((scale - 1) * percent + 1);
        }
        if (rightView != null) {
            rightView.setScaleY((1 - scale) * percent + scale);
        }
    }
    
    private static class CardLinearSnapHelper extends LinearSnapHelper {
        public boolean isNoNeedToScroll = false;
        public int[] finalSnapDistance = {0, 0};

        @Override
        public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
            if (isNoNeedToScroll) {
                finalSnapDistance[0] = 0;
                finalSnapDistance[1] = 0;
            } else {
                finalSnapDistance = super.calculateDistanceToFinalSnap(layoutManager, targetView);
            }
            return finalSnapDistance;
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            recyclerViewWidth = GalleryScaleHelper.this.recyclerView.getWidth();
            cardWidth = recyclerViewWidth - dip2px(2 * (pagePadding + showLeftCardWidth));
            scrollOneCardWidth = cardWidth;
            scrollToPosition(firstPosition);
        }
    };

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
//            LogUtil.e(TAG,"onScrollStateChanged newState == "+newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                snapHelper.isNoNeedToScroll = getPosition() == 0 ||
                        getPosition() == recyclerView.getAdapter().getItemCount() - 2;
                if (snapHelper.finalSnapDistance[0] == 0
                        && snapHelper.finalSnapDistance[1] == 0) {
                    currentOffset = 0;
                    lastPosition = getPosition();

                    ((GalleryRecyclerView) recyclerView)
                            .dispatchOnPageSelected(lastPosition);
                }
            } else {
                snapHelper.isNoNeedToScroll = false;
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            currentOffset += dx;
            onScrolledChangedCallback();
        }
    };

    private int dip2px(float dip){
        return (int)(recyclerView.getContext().getResources().getDisplayMetrics().density*dip);
    }
}