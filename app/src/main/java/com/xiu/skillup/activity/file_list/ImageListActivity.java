package com.xiu.skillup.activity.file_list;

import android.view.ViewGroup;

import com.xiu.datalib.common.MediaInfo;
import com.xiu.skillup.R;
import com.xiu.skillup.adapter.SingleImageAdapter;
import com.xiu.skillup.presenter.file_list.ImageFileListPresenter;
import com.xiu.ui.view.recycler.XHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ImageListActivity
        extends BaseFileListActivity<String, SingleImageAdapter, ImageFileListPresenter> {

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        setTitle("图片列表");
    }

    @NonNull
    @Override
    public ImageFileListPresenter createPresenter() {
        return new ImageFileListPresenter();
    }

    @Override
    public SingleImageAdapter createAdapter() {
        return new SingleImageAdapter(getContext()) {
            @NonNull
            @Override
            public XHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                XHolder holder = super.onCreateViewHolder(parent, viewType);
                int size =
                        getContext().getResources().getDisplayMetrics().widthPixels/2;
                holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(size,size));
                holder.itemView.setBackgroundColor(getResources().getColor(R.color.gray));
                return holder;
            }
        };
    }

    @Override
    public void onItemClick(int id, int position, String data) {

    }

    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        return new GridLayoutManager(getContext(), 2);
    }
}
