package com.lvshou.cloudreaadercopy.weiget.banner;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.lvshou.cloudreaadercopy.R;
import com.lvshou.utils.LogUtil;
import com.sunfusheng.glideimageview.progress.GlideApp;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Lenovo on 2017/12/7.
 * 首页轮播图
 */

public class BannerImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        GlideApp.with(imageView)
                .load((String)path)
                .placeholder(R.mipmap.img_two_bi_one)
                .error(R.mipmap.img_two_bi_one)
                .transition(new DrawableTransitionOptions().crossFade(1000))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
