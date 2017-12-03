package com.lvshou.cloudreaadercopy.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lvshou.cloudreaadercopy.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by liuxiu on 2016/11/30.
 * 首页轮播图
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .placeholder(R.mipmap.img_two_bi_one)
                .error(R.mipmap.img_two_bi_one)
                .crossFade(1000)
                .into(imageView);
    }
}
