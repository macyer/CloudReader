package com.macyer.cloudreaadercopy.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.macyer.cloudreaadercopy.R;

import java.util.HashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Lenovo on 2017/12/1.
 */

public abstract class BaseFragment<SV extends ViewDataBinding> extends Fragment {

    protected SV bindingView;
    private RelativeLayout mContainer;
    private LinearLayout mLlProgressBar, mLlRefresh;
    private AnimationDrawable mAnimationDrawable;

    /**
     * mIsVisible 是否可见
     * mIsPrepared view初始化完毕
     */
    private boolean mIsVisible, mIsPrepared;
    /**
     * 是否第一次展示
     */
    protected boolean mIsFirst = true;

    private HashMap<String, CompositeDisposable> mDisposableMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ll = inflater.inflate(R.layout.fragment_base, null);
        bindingView = DataBindingUtil.inflate(inflater, setContentView(), null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        bindingView.getRoot().setLayoutParams(params);
        mContainer = (RelativeLayout) ll.findViewById(R.id.container);
        mContainer.addView(bindingView.getRoot());
        return ll;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLlProgressBar = getView(R.id.ll_progress_bar);
        ImageView img = getView(R.id.img_progress);
        mAnimationDrawable = (AnimationDrawable) img.getDrawable();
        if (!mAnimationDrawable.isRunning()) mAnimationDrawable.start();
        mLlRefresh = getView(R.id.ll_error_refresh);
        mLlRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                onRefresh();
            }
        });
        //TODO gone是什么意思
//        bindingView.getRoot().setVisibility(View.GONE);
        initView();
        showContentView();
        mIsPrepared = true;
        loadData();
    }

    protected <T extends View> T getView(int id) {
        return (T) getView().findViewById(id);
    }

    /**
     * 在这里实现fragment数据的缓加载
     *
     * @return
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInVisible();
        }
    }

    private void onVisible() {
        //fragment不可见或者未init完毕不加载数据
        if (!mIsVisible || !mIsPrepared) return;
        loadData();
    }

    protected void onInVisible() {

    }

    protected void showLoading() {
        if (mLlProgressBar.getVisibility() != View.VISIBLE)
            mLlProgressBar.setVisibility(View.VISIBLE);
        if (!mAnimationDrawable.isRunning())
            mAnimationDrawable.start();
        if (bindingView.getRoot().getVisibility() != View.GONE)
            bindingView.getRoot().setVisibility(View.GONE);
        if (mLlRefresh.getVisibility() != View.GONE)
            mLlRefresh.setVisibility(View.GONE);
    }

    protected void showContentView() {
        if (mLlProgressBar.getVisibility() != View.GONE)
            mLlProgressBar.setVisibility(View.GONE);
        if (mAnimationDrawable.isRunning())
            mAnimationDrawable.stop();
        if (mLlRefresh.getVisibility() != View.GONE)
            mLlRefresh.setVisibility(View.GONE);
        if (bindingView.getRoot().getVisibility() != View.VISIBLE)
            bindingView.getRoot().setVisibility(View.VISIBLE);
    }

    protected void errLoading() {
        if (mLlProgressBar.getVisibility() != View.GONE)
            mLlProgressBar.setVisibility(View.GONE);
        if (mAnimationDrawable.isRunning())
            mAnimationDrawable.stop();
        if (mLlRefresh.getVisibility() != View.VISIBLE)
            mLlRefresh.setVisibility(View.VISIBLE);
        if (bindingView.getRoot().getVisibility() != View.GONE)
            bindingView.getRoot().setVisibility(View.GONE);
    }

    /**
     * 设置fragment布局
     *
     * @return
     */
    public abstract @LayoutRes
    int setContentView();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 显示时加载数据,需要这样的使用
     * 注意声明 isPrepared，先初始化
     * 生命周期会先执行 setUserVisibleHint 再执行onActivityCreated
     * 在 onActivityCreated 之后第一次显示加载数据，只加载一次
     */
    protected abstract void loadData();

    /**
     * 刷新数据
     */
    protected void onRefresh() {

    }

    protected void addBaseDisposable(Disposable disposable) {
        if (mDisposableMap == null) {
            mDisposableMap = new HashMap<>();
        }
        String key = getClass().getSimpleName();
        if (mDisposableMap.get(key) != null) {
            mDisposableMap.get(key).add(disposable);
        } else {
            mDisposableMap.put(key, new CompositeDisposable(disposable));
        }
    }

    private void removeDisposable() {
        if (mDisposableMap == null) return;
        String key = getClass().getSimpleName();
        if (!mDisposableMap.containsKey(key)) return;

        if (mDisposableMap.get(key) != null) {
            mDisposableMap.get(key).dispose();
        }
        mDisposableMap.remove(key);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeDisposable();
    }
}
