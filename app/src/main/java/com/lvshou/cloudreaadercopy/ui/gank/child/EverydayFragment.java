package com.lvshou.cloudreaadercopy.ui.gank.child;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lvshou.cloudreaadercopy.R;
import com.lvshou.cloudreaadercopy.base.BaseFragment;
import com.lvshou.cloudreaadercopy.databinding.FooterItemEverydayBinding;
import com.lvshou.cloudreaadercopy.databinding.FragmentGankEverydayBinding;
import com.lvshou.cloudreaadercopy.databinding.HeaderItemEverydayBinding;
import com.lvshou.xrecyclerview.XRecyclerView;

/**
 * Created by Lenovo on 2017/12/1.
 */

public class EverydayFragment extends BaseFragment<FragmentGankEverydayBinding> {

    private LinearLayout ll_loading;
    private XRecyclerView recyclerView;
    private ImageView ivLoading;
    private RotateAnimation animation;
    
    private HeaderItemEverydayBinding headerBinding;
    private FooterItemEverydayBinding footerBinding;
    private View mHeaderView;
    private View mFooterView;

    @Override
    public int setContentView() {
        return R.layout.fragment_gank_everyday;
    }

    @Override
    protected void initView() {
        initIds();
        initAnimation();
        initRecyclerView();
        
    }

    private void initRecyclerView() {
        recyclerView.setLoadingMoreEnabled(false);
        recyclerView.setPullRefreshEnabled(false);
        if (mHeaderView == null){
            mHeaderView = headerBinding.getRoot();
            recyclerView.addHeaderView(mHeaderView);
        }
        if (mFooterView == null){
            footerBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.footer_item_everyday,null,false);
            mFooterView = footerBinding.getRoot();
            recyclerView.addFootView(mFooterView,true);
            recyclerView.noMoreLoading();
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initIds() {
        ll_loading = getView(R.id.ll_loading);
        recyclerView = getView(R.id.xrv_everyday);
        ivLoading = getView(R.id.iv_loading);
        headerBinding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.header_item_everyday,null,false);
    }

    private void initAnimation() {
        ll_loading.setVisibility(View.VISIBLE);
        animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(3000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(10);
        ivLoading.setAnimation(animation);
        animation.startNow();
    }


    private void showLoadingThis(boolean isLoading) {
        ll_loading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        if (isLoading) animation.startNow();
        else animation.cancel();
    }

    @Override
    protected void loadData() {

    }
}
