package com.xiu.ui.view.recycler.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiu.common.utils.ArrayUtil;
import com.xiu.ui.R;
import com.xiu.ui.view.recycler.XHolder;
import com.xiu.ui.view.recycler.XRecyclerAdapter;

import androidx.annotation.NonNull;

public abstract class GalleryBaseAdapter<T> extends XRecyclerAdapter<T> {

    private GalleryAdapterHelper helper;

    public GalleryBaseAdapter(Context context) {
        super(context);
        helper = new GalleryAdapterHelper();
    }

    @NonNull
    @Override
    public XHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(setLayoutId(), parent, false);
        XHolder holder = new XHolder(itemView);
        holder.itemView.setId(R.id.item_gallery_btn);
        setXHolderClick(holder);
        helper.onCreateViewHolder(parent, holder.itemView);
        return holder;
    }

    @Override
    public int[] setOnClick() {
        return new int[]{R.id.item_gallery_btn};
    }

    @Override
    public boolean onClick(int id, int position, T data) {
        return false;
    }

    @Override
    public void onClick(View view) {
        Object[] tags = (Object[]) view.getTag();
        if (ArrayUtil.isObjectArrayNotEmpty(tags)) {
            int viewId = (int) tags[0];
            int position = (int) tags[1];
            XHolder holder = (XHolder) tags[2];
            if (viewId == R.id.item_gallery_btn) {
                ((GalleryRecyclerView) holder.itemView.getParent()).smoothScrollToPosition(position);
                return;
            }
            int realPosition = getRealPosition(position);
            if (GalleryBaseAdapter.this.onClick(viewId, realPosition,
                    getItem(getRealPosition(position))) && onItemClickLister != null) {
                onItemClickLister.onItemClick(realPosition, getItem(realPosition));
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull XHolder holder, int position) {
        if (ArrayUtil.isIntArrayNotEmpty(setOnClick())) {
            for (int id : setOnClick()) {
                holder.findViewById(id).setTag(new Object[]{id, position, holder});
            }
        }
        helper.onBindViewHolder(holder.itemView, position, getItemCount());
        onBindViewHolder(holder, getRealPosition(position), getItem(position));
    }


    @Override
    public void setOnItemClickLister(OnItemClickLister<T> onItemClickLister) {
        super.setOnItemClickLister(onItemClickLister);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public GalleryAdapterHelper getGalleryAdapterHelper() {
        return helper;
    }


    @Override
    public T getItem(int position) {
        return super.getItem(getRealPosition(position));
    }

    public int getRealPosition(int position){
        return position%getData().size();
    }
}