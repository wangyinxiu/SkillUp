package com.xiu.photopicker.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.xiu.common.utils.LogUtil;
import com.xiu.datalib.image.Image;
import com.xiu.photopicker.R;
import com.xiu.ui.view.recycler.XHolder;
import com.xiu.ui.view.recycler.XRecyclerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class AlbumSelectedAdapter extends XRecyclerAdapter<Image> {

    private static final String TAG = "AlbumSelectedAdapter";

    private int itemWidth;
    private ArrayList<Image> selected;
    private int maxCount;

    public AlbumSelectedAdapter(Context context, int width, int maxCount) {
        super(context);
        itemWidth = width;
        this.maxCount = maxCount;
        selected = new ArrayList<>();
    }

    @NonNull
    @Override
    public XHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        XHolder holder = super.onCreateViewHolder(parent, viewType);
        holder.itemView.setLayoutParams(new RelativeLayout.LayoutParams(itemWidth, itemWidth));
        return holder;
    }

    @Override
    public int setLayoutId() {
        return R.layout.item_photo_picker;
    }

    @Override
    public int[] setOnClick() {
        return new int[]{R.id.item_image};
    }

    @Override
    public boolean onClick(int id, int position, Image data) {
        if(selected.contains(data)){
            selected.remove(data);
        }else{
            if(selected.size() < maxCount){
                selected.add(data);
            }else {
                return false;
            }
        }
        notifyItemChanged(position);
        return true;
    }

    @Override
    public void onBindViewHolder(@NonNull XHolder holder, int position, Image data) {
        LogUtil.i(TAG, "image path == " + data.getPath());
        ImageView imageView = holder.itemView.findViewById(R.id.item_image);
        Picasso
                .get()
                .load(new File(data.getPath()))
                .resize(itemWidth, itemWidth)
                .centerCrop()
                .into(imageView);
        int visibility = View.GONE;
        if (selected.contains(data)) {
            visibility = View.VISIBLE;
        }
        holder.findViewById(R.id.item_select).setVisibility(visibility);
        holder.findViewById(R.id.item_mask).setVisibility(visibility);
    }

    public int getSelectedCount() {
        return selected.size();
    }

    public ArrayList<Image> getSelected(){
        return selected;
    }
}
