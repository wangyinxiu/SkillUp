package com.xiu.skillup.adapter;

import android.content.Context;

import com.xiu.common.utils.LogUtil;
import com.xiu.skillup.R;
import com.xiu.ui.view.recycler.XHolder;
import com.xiu.ui.view.recycler.XRecyclerAdapter;

import androidx.annotation.NonNull;

public class SingleButtonAdapter extends XRecyclerAdapter<String> {

    private static final String TAG = "SingleButtonAdapter";

    public SingleButtonAdapter(Context context) {
        super(context);
    }

    @Override
    public int setLayoutId() {
        return R.layout.item_single_text;
    }

    @Override
    public int[] setOnClick() {
        return new int[]{R.id.item_text};
    }

    @Override
    public boolean onClick(int id, int position, String data) {
        LogUtil.i(TAG, "position == " + position + " , data == " + data);
        return true;
    }

    @Override
    public void onBindViewHolder(@NonNull XHolder holder, int position, String data) {
        holder.findButtonById(R.id.item_text).setTextSafe(data);
    }
}
