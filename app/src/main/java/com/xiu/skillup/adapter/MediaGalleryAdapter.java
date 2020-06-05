package com.xiu.skillup.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;

import com.xiu.datalib.common.MediaInfo;
import com.xiu.skillup.R;
import com.xiu.ui.view.recycler.XHolder;
import com.xiu.ui.view.recycler.gallery.GalleryBaseAdapter;

import androidx.annotation.NonNull;

public class MediaGalleryAdapter extends GalleryBaseAdapter<MediaInfo> {

    public MediaGalleryAdapter(Context context) {
        super(context);
    }

    @Override
    public int setLayoutId() {
        return R.layout.item_media_gallery;
    }



    @Override
    public void onBindViewHolder(@NonNull XHolder holder, int position, MediaInfo data) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(data.getPath());
        byte[] bitmapData = retriever.getEmbeddedPicture();
        if(bitmapData != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapData,0,
                    bitmapData.length);
            holder.findImageViewById(R.id.item_image).setImageBitmap(bitmap);
        }else {
            holder.findImageViewById(R.id.item_image)
                    .setImageDrawable(new ColorDrawable(context.getResources().getColor(R.color.gray)));
        }

    }
}
