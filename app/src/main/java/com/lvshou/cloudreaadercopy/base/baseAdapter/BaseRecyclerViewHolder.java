package com.lvshou.cloudreaadercopy.base.baseAdapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by Lenovo on 2017/12/5.
 */

public abstract class BaseRecyclerViewHolder<T,D extends ViewDataBinding> extends RecyclerView.ViewHolder {
    
    protected D binding;
    
    public BaseRecyclerViewHolder(ViewGroup viewGroup, @LayoutRes int layoutId) {
        //需要viewgroup，否则显示不全
        super(DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),layoutId,viewGroup,false).getRoot());
        //获取bindingview
        binding = DataBindingUtil.getBinding(this.itemView);
        
    }
    
    public abstract void bindViewHolder(T t,int position);

    /**
     * 当数据改变时，binding会在下一帧去改变数据，如果我们需要立即改变，就去调用executePendingBindings方法。
     */
    private void onBaseBindViewHolder(T t,int position){
        bindViewHolder(t,position);
        binding.executePendingBindings();
    }
}
