package com.xiu.ui.view.recycler.gallery;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.xiu.common.utils.LogUtil;

import androidx.recyclerview.widget.RecyclerView;

public class GalleryAdapterHelper {

    public int pagePadding =20;
    public int showLeftCardWidth = 45;
    private int width;
    private int height;

    private int dip2px(Context context,float dip){
        return (int)(context.getResources().getDisplayMetrics().density*dip);
    }

    public void onCreateViewHolder(ViewGroup parent,  View itemView) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
        width = params.width = parent.getWidth() -
                        dip2px(itemView.getContext(), 2 * (pagePadding + showLeftCardWidth));
        height = params.height;
        itemView.setLayoutParams(params);

    }

    public void onBindViewHolder(View itemView, final int position, int itemCount) {
        int padding = dip2px(itemView.getContext(), pagePadding);
        itemView.setPadding(padding, 0, padding, 0);
        int leftMarin = position == 0 ? padding + dip2px(itemView.getContext(), showLeftCardWidth) : padding;
        int rightMarin = position == itemCount - 1 ? padding + dip2px(itemView.getContext(), showLeftCardWidth) : padding;
        setViewMargin(itemView, leftMarin, 0, rightMarin, 0);
    }

    private void setViewMargin(View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.setMargins(left, top, right, bottom);
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    public void setPagePadding(int pagePadding) {
        this.pagePadding = pagePadding;
    }

    public void setShowLeftCardWidth(int showLeftCardWidth) {
        this.showLeftCardWidth = showLeftCardWidth;
    }

    public int getPagePadding() {
        return pagePadding;
    }

    public int getShowLeftCardWidth() {
        return showLeftCardWidth;
    }
}