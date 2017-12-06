package com.lvshou.cloudreaadercopy.base.baseAdapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2017/12/5.
 */

public abstract class BaseRecyclerAdaper<T> extends RecyclerView.Adapter<BaseRecyclerViewHolder> {

    private List<T> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    @Override
    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        holder.bindViewHolder(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<T> getData() {
        return list;
    }

    public void addAll(List<T> list) {
        this.list = list;
    }

    public void addItem(T t) {
        this.list.add(t);
    }

    public void clear() {
        this.list.clear();
    }

    public void remove(int positon) {
        this.list.remove(positon);
    }

    public void remove(T t) {
        this.list.remove(t);
    }

    public void removeAll(List<T> list) {
        this.list.retainAll(list);
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener){
        this.onItemClickListener = listener;
    }
    
    public void setOnItemLongClickListener(OnItemLongClickListener<T> longClickListener){
        this.onItemLongClickListener = longClickListener;
    }
}
