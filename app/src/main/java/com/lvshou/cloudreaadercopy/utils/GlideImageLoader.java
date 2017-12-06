package com.lvshou.cloudreaadercopy.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.lvshou.cloudreaadercopy.R;
import com.sunfusheng.glideimageview.progress.GlideApp;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by liuxiu on 2016/11/30.
 * 首页轮播图
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object url, ImageView imageView) {
        GlideApp.with(imageView).load(url)
                .placeholder(R.mipmap.img_two_bi_one)
                .error(R.mipmap.img_two_bi_one)
                .transition(new DrawableTransitionOptions().crossFade(1000))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
