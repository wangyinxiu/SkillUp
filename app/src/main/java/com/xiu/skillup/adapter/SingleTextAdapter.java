package com.xiu.skillup.adapter;

import android.content.Context;

import com.xiu.common.utils.LogUtil;
import com.xiu.skillup.R;
import com.xiu.ui.view.recycler.XHolder;
import com.xiu.ui.view.recycler.XRecyclerAdapter;

import androidx.annotation.NonNull;

public class SingleTextAdapter<T> extends XRecyclerAdapter<T> {

    private static final String TAG = "SingleButtonAdapter";

    public SingleTextAdapter(Context context) {
        super(context);
    }

    @Override
    public int setLayoutId() {
        return R.layout.item_single_button;
    }

    @Override
    public int[] setOnClick() {
        return new int[]{R.id.item_text};
    }

    @Override
    public boolean onClick(int id, int position, T data) {
        return true;
    }

    @Override
    public void onBindViewHolder(@NonNull XHolder holder, int position, T data) {
        holder.findButtonById(R.id.item_text).setBackgroundColor(context.getResources().getColor(R.color.transparent));
    }
}
