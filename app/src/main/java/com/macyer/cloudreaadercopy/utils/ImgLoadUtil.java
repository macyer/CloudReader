package com.macyer.cloudreaadercopy.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.macyer.cloudreaadercopy.R;
import com.macyer.glideimageview.progress.GlideApp;
import com.macyer.utils.CommonUtils;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by liuxiu on 2016/11/26.
 */

public class ImgLoadUtil {
    
    private static ImgLoadUtil instance;

    private ImgLoadUtil() {
    }

    public static ImgLoadUtil getInstance() {
        if (instance == null) {
            instance = new ImgLoadUtil();
        }
        return instance;
    }


    /**
     * 显示随机的图片(每日推荐)
     *
     * @param imgNumber 有几张图片要显示,对应默认图
     * @param imageUrl  显示图片的url
     * @param imageView 对应图片控件
     */
    public static void displayRandom(int imgNumber, String imageUrl, ImageView imageView) {
        GlideApp.with(imageView)
                .load(imageUrl)
                .placeholder(getMusicDefaultPic(imgNumber))
                .error(getMusicDefaultPic(imgNumber))
                .transition(new DrawableTransitionOptions().crossFade(1500))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(imageView);
    }

    private static int getMusicDefaultPic(int imgNumber) {
        switch (imgNumber) {
            case 1:
                return R.mipmap.img_two_bi_one;
            case 2:
                return R.mipmap.img_four_bi_three;
            case 3:
                return R.mipmap.img_one_bi_one;
        }
        return R.mipmap.img_four_bi_three;
    }

//--------------------------------------

    /**
     * 用于干货item，将gif图转换为静态图
     */
    public static void displayGif(String url, ImageView imageView) {
        GlideApp.with(imageView)
                .asGif()
                .transition(new GenericTransitionOptions<GifDrawable>())
                .load(url)
                .error(R.mipmap.img_one_bi_one)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(imageView);
    }

    /**
     * 书籍、妹子图、电影列表图
     * 默认图区别
     */
    public static void displayEspImage(String url, ImageView imageView, int type) {
        GlideApp.with(imageView)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .placeholder(getDefaultPic(type))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(getDefaultPic(type))
                .into(imageView);
    }

    private static int getDefaultPic(int type) {
        switch (type) {
            case 0:// 电影
                return R.mipmap.img_default_movie;
            case 1:// 妹子
                return R.mipmap.img_default_meizi;
            case 2:// 书籍
                return R.mipmap.img_default_book;
        }
        return R.mipmap.img_default_meizi;
    }

    /**
     * 显示高斯模糊效果（电影详情页）
     */
    private static void displayGaussian(String imageUrl, ImageView imageView) {
        // "23":模糊度；"4":图片缩放4倍后再进行模糊
        GlideApp.with(imageView)
                .load(imageUrl)
                .error(R.mipmap.stackblur_default)
                .placeholder(R.mipmap.stackblur_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .transform(new MultiTransformation<>(new GlideCircleTransform(imageView.getContext()),new BlurTransformation(imageView.getContext(),34,4)))
                .into(imageView);
    }

    /**
     * 加载圆角图,暂时用到显示头像
     */
    public static void displayCircle(ImageView imageView, String imageUrl) {
        GlideApp.with(imageView)
                .load(imageUrl)
                .error(R.mipmap.ic_avatar_default)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 妹子，电影列表图
     *
     * @param defaultPicType 电影：0；妹子：1； 书籍：2
     */
    @BindingAdapter({"android:displayFadeImage", "android:defaultPicType"})
    public static void displayFadeImage(ImageView imageView, String url, int defaultPicType) {
        displayEspImage(url, imageView, defaultPicType);
    }

    /**
     * 电影详情页显示电影图片(等待被替换)（测试的还在，已可以弃用）
     * 没有加载中的图
     */
    @BindingAdapter("android:showImg")
    public static void showImg(ImageView imageView, String url) {
        GlideApp.with(imageView)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .error(getDefaultPic(0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 电影列表图片
     */
    @BindingAdapter("android:showMovieImg")
    public static void showMovieImg(ImageView imageView, String url) {
        GlideApp.with(imageView)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .override((int) CommonUtils.getDimens(R.dimen.movie_detail_width), (int) CommonUtils.getDimens(R.dimen.movie_detail_height))
                .placeholder(getDefaultPic(0))
                .error(getDefaultPic(0))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 书籍列表图片
     */
    @BindingAdapter("android:showBookImg")
    public static void showBookImg(ImageView imageView, String url) {
        GlideApp.with(imageView)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade(500))
                .override((int) CommonUtils.getDimens(R.dimen.movie_detail_width), (int) CommonUtils.getDimens(R.dimen.movie_detail_height))
                .placeholder(getDefaultPic(2))
                .error(getDefaultPic(2))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    /**
     * 电影详情页显示高斯背景图
     */
    @BindingAdapter("android:showImgBg")
    public static void showImgBg(ImageView imageView, String url) {
        displayGaussian(url, imageView);
    }
}
