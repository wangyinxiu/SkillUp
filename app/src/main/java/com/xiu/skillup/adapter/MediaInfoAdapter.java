package com.xiu.skillup.adapter;

import android.content.Context;

import com.xiu.datalib.common.MediaInfo;
import com.xiu.skillup.R;
import com.xiu.ui.view.recycler.XHolder;
import com.xiu.ui.view.recycler.XRecyclerAdapter;

import java.util.List;

import androidx.annotation.NonNull;

public class MediaInfoAdapter extends XRecyclerAdapter<MediaInfo> {

    public MediaInfoAdapter(Context context) {
        super(context);
    }

    @Override
    public int setLayoutId() {
        return R.layout.item_media_info;
    }

    @Override
    public int[] setOnClick() {
        return new int[]{};
    }

    @Override
    public boolean onClick(int id, int position, MediaInfo data) {
        return false;
    }

    @Override
    public void onBindViewHolder(@NonNull XHolder holder, int position, MediaInfo data) {
        holder.findTextViewById(R.id.item_title).setTextSafe(data.getTitle());
        holder.findTextViewById(R.id.item_album).setTextSafe(data.getAlbum());
        holder.findTextViewById(R.id.item_artist).setTextSafe(data.getArtist());
        holder.findTextViewById(R.id.item_path).setTextSafe(data.getPath());
    }

    public void disassembleData(List<String> data){

    }

}
