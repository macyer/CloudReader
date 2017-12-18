package com.lvshou.cloudreaadercopy.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lvshou.cloudreaadercopy.R;
import com.lvshou.cloudreaadercopy.databinding.ActivityBaseBinding;
import com.lvshou.utils.CommonUtils;
import com.lvshou.utils.PerfectClickListener;
import com.lvshou.widget.statusbar.StatusBarUtil;

import org.reactivestreams.Subscription;

import java.util.HashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Lenovo on 2017/12/18.
 */

public class BaseActivity<SV extends ViewDataBinding> extends AppCompatActivity {

    public SV mBinding;
    private ActivityBaseBinding mActivityBaseBinding;
    private LinearLayout llProgressBar;
    private LinearLayout mLlRefresh;
    private AnimationDrawable mAnimationDrawable;

    private HashMap<String, CompositeDisposable> mDisposableMap;
    
    protected <T extends View> T getView(@IdRes int resId){
        return (T)findViewById(resId);
    }

    @Override
    public void setContentView(int layoutResID) {
        mActivityBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base,null,false);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(this), layoutResID,null,false);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        mBinding.getRoot().setLayoutParams(layoutParams);
        RelativeLayout container = mActivityBaseBinding.container;
        container.addView(mBinding.getRoot());
        getWindow().setContentView(mActivityBaseBinding.getRoot());

        StatusBarUtil.setColor(this, CommonUtils.getColor(R.color.colorTheme),0);
        llProgressBar = mActivityBaseBinding.llProgressBar;
        mLlRefresh = mActivityBaseBinding.llErrorRefresh;
        ImageView imageView = mActivityBaseBinding.imgProgress;
        mAnimationDrawable = (AnimationDrawable) imageView.getDrawable();
        if (mAnimationDrawable != null && !mAnimationDrawable.isRunning()){
            mAnimationDrawable.start();
        }
        setToolBar();
        mActivityBaseBinding.llErrorRefresh.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                showLoading();
                onRefresh();
            }
        });
        mBinding.getRoot().setVisibility(View.GONE);
    }


    /**
     * 设置titlebar
     */
    protected void setToolBar() {
        setSupportActionBar(mActivityBaseBinding.toolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //去除默认Title显示
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.icon_back);
        }
        mActivityBaseBinding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setTitle(CharSequence text) {
        mActivityBaseBinding.toolBar.setTitle(text);
    }

    protected void showLoading() {
        if (llProgressBar.getVisibility() != View.VISIBLE) {
            llProgressBar.setVisibility(View.VISIBLE);
        }
        // 开始动画
        if (!mAnimationDrawable.isRunning()) {
            mAnimationDrawable.start();
        }
        if (mBinding.getRoot().getVisibility() != View.GONE) {
            mBinding.getRoot().setVisibility(View.GONE);
        }
        if (mLlRefresh.getVisibility() != View.GONE) {
            mLlRefresh.setVisibility(View.GONE);
        }
    }

    protected void showContentView() {
        if (llProgressBar.getVisibility() != View.GONE) {
            llProgressBar.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (mLlRefresh.getVisibility() != View.GONE) {
            mLlRefresh.setVisibility(View.GONE);
        }
        if (mBinding.getRoot().getVisibility() != View.VISIBLE) {
            mBinding.getRoot().setVisibility(View.VISIBLE);
        }
    }

    protected void showError() {
        if (llProgressBar.getVisibility() != View.GONE) {
            llProgressBar.setVisibility(View.GONE);
        }
        // 停止动画
        if (mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
        }
        if (mLlRefresh.getVisibility() != View.VISIBLE) {
            mLlRefresh.setVisibility(View.VISIBLE);
        }
        if (mBinding.getRoot().getVisibility() != View.GONE) {
            mBinding.getRoot().setVisibility(View.GONE);
        }
    }

    /**
     * 失败后点击刷新
     */
    protected void onRefresh() {

    }
    
    public void addDisposable(Disposable disposable) {
        if (this.mDisposableMap == null) {
            this.mDisposableMap = new HashMap();
        }
        String key = getClass().getSimpleName();
        if (mDisposableMap.get(key) != null) {
            mDisposableMap.get(key).add(disposable);
        } else {
            mDisposableMap.put(key, new CompositeDisposable(disposable));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeDisposable();
    }

    public void removeDisposable() {
        if (mDisposableMap == null) return;
        String key = getClass().getSimpleName();
        if (!mDisposableMap.containsKey(key)) return;

        if (mDisposableMap.get(key) != null) {
            mDisposableMap.get(key).dispose();
        }
        mDisposableMap.remove(key);
    }
}
