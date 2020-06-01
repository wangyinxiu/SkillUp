package com.xiu.skillup.adapter;

import android.content.Context;

import com.xiu.skillup.R;
import com.xiu.ui.view.recycler.XHolder;
import com.xiu.ui.view.recycler.XRecyclerAdapter;

import androidx.annotation.NonNull;

public class MainAdapter extends XRecyclerAdapter<String> {


    public MainAdapter(Context context) {
        super(context);
    }

    @Override
    public int setLayoutId() {
        return R.layout.item_single_text;
    }

    @Override
    public int[] setOnClick() {
        return new int[]{R.id.item_btn};
    }

    @Override
    public boolean onClick(int id, int position, String data) {
        return true;
    }

    @Override
    public void onBindViewHolder(@NonNull XHolder holder, int position, String data) {
        holder.findButtonById(R.id.item_btn).setTextSafe(data);
    }
}
