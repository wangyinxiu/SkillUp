package com.xiu.ui.view.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiu.common.utils.ArrayUtil;
import com.xiu.common.utils.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class XRecyclerAdapter<T> extends RecyclerView.Adapter<XHolder> {

    private List<T> data = null;
    private Context context = null;
    private View.OnClickListener onClickListener;
    private OnItemClickLister<T> onItemClickLister;

    public XRecyclerAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public XRecyclerAdapter(List<T> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public abstract int setLayoutId();

    public abstract int[] setOnClick();

    public abstract void onClick(int id, int position, T data);

    public abstract void onBindViewHolder(@NonNull XHolder holder,
                                          int position, T data);


    @NonNull
    @Override
    public XHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(setLayoutId(), parent, false);
        XHolder holder = new XHolder(itemView);
        setXHolderClick(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull XHolder holder, int position) {
        if (ArrayUtil.isIntArrayNotEmpty(setOnClick())) {
            for (int id : setOnClick()) {
                holder.findViewById(id).setTag(new int[]{id, position});
            }
        }
        onBindViewHolder(holder, position, getItem(position));
    }

    public void setXHolderClick(XHolder holder) {
        if (ArrayUtil.isIntArrayNotEmpty(setOnClick())) {
            onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int[] tags = (int[]) view.getTag();
                    if (ArrayUtil.isIntArrayNotEmpty(tags)) {
                        if (onItemClickLister == null) {
                            XRecyclerAdapter.this.onClick(tags[0], tags[1],
                                    getItem(tags[1]));
                        } else {
                            onItemClickLister.onItemClick(tags[0], tags[1],
                                    getItem(tags[1]));
                        }
                    }
                }
            };
            for (int id : setOnClick()) {
                holder.findViewById(id).setOnClickListener(onClickListener);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public T getItem(int position) {
        T data = null;
        if (position >= 0 || position < this.data.size()) {
            data = this.data.get(position);
        }
        return data;
    }

    public void addData(T t) {
        if (t != null) {
            data.add(t);
            notifyDataSetChanged();
        }
    }

    public void addData(T t, int index) {
        if (t != null) {
            data.add(index, t);
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> list) {
        if (ListUtil.isNotEmpty(list)) {
            data.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void addData(List<T> list, int index) {
        if (ListUtil.isNotEmpty(list)) {
            data.addAll(index, list);
            notifyDataSetChanged();
        }
    }

    public void remove(int index) {
        if (index >= 0 && index < data.size()) {
            data.remove(index);
            notifyDataSetChanged();
        }
    }

    public void remove(T t) {
        if (data.contains(t)) {
            data.remove(t);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        if (data.size() > 0) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    public int getItemPosition(T t) {
        int result = -1;
        if (data.contains(t)) {
            result = data.indexOf(t);
        }
        return result;
    }

    public void setOnItemClickLister(OnItemClickLister<T> onItemClickLister){
        this.onItemClickLister = onItemClickLister;
    }


    public void sort(Comparator<T> comparator) {
        Collections.sort(data, comparator);
        notifyDataSetChanged();
    }

    public interface OnItemClickLister<T> {
        void onItemClick(int id, int position, T data);
    }
}
