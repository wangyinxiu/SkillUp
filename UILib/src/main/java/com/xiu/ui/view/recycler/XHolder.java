package com.xiu.ui.view.recycler;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.xiu.ui.image.PicassoUtil;
import com.xiu.ui.view.XTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class XHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> viewCache = null;

    public XHolder(@NonNull View itemView) {
        super(itemView);
        viewCache = new SparseArray<>();
    }

    public <T extends View> T findViewById(int id){
        View view = viewCache.get(id);
        if(view == null){
            view = itemView.findViewById(id);
            viewCache.put(id,view);
        }
        return (T)view;
    }

    public XTextView findTextViewById(int id){
        return findViewById(id);
    }

    public ImageView findImageViewById(int id){
        return findViewById(id);
    }

    public void setImage(int id,String path){
        PicassoUtil.load(path,findImageViewById(id));
    }

    public void setImage(int id,int resId){
        PicassoUtil.load(resId,findImageViewById(id));
    }


}
