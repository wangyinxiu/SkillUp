package com.xiu.skillup.adapter;

import android.content.Context;

import com.squareup.picasso.Picasso;
import com.xiu.skillup.R;
import com.xiu.ui.view.recycler.XHolder;
import com.xiu.ui.view.recycler.XRecyclerAdapter;

import java.io.File;

import androidx.annotation.NonNull;

public class SingleImageAdapter extends XRecyclerAdapter<String> {

    public SingleImageAdapter(Context context) {
        super(context);
    }

    @Override
    public int setLayoutId() {
        return R.layout.item_single_image;
    }

    @Override
    public int[] setOnClick() {
        return new int[]{R.id.item_image};
    }

    @Override
    public boolean onClick(int id, int position, String data) {
        return true;
    }

    @Override
    public void onBindViewHolder(@NonNull XHolder holder, int position, String data) {
        Picasso
                .get()
                .load(new File(data))
                .centerCrop()
                .resize(200,200)
                .into(holder.findImageViewById(R.id.item_image));
    }
}
