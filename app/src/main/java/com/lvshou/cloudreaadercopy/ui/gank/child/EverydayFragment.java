package com.lvshou.cloudreaadercopy.ui.gank.child;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.DefaultItemAnimator;
import android.util.Log;
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
import com.lvshou.cloudreaadercopy.http.HttpClient;
import com.lvshou.cloudreaadercopy.http.RequestImpl;
import com.lvshou.cloudreaadercopy.model.EverydayModel;
import com.lvshou.cloudreaadercopy.utils.TimeUtil;
import com.lvshou.xrecyclerview.XRecyclerView;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

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
    private EverydayModel everydayModel;

    @Override
    public int setContentView() {
        return R.layout.fragment_gank_everyday;
    }

    @Override
    protected void initView() {
        
        initIds();
        
        initAnimation();
        
        everydayModel = new EverydayModel();
        
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

    /**
     * 显示此界面内部的Loading view
     * @param isLoading
     */
    private void showLoadingThis(boolean isLoading) {
        ll_loading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        if (isLoading) animation.startNow();
        else animation.cancel();
    }

    @Override
    protected void loadData() {

        showLoadingThis(true);
        everydayModel.setData(getTodayTime().get(0),getTodayTime().get(1),getTodayTime().get(2));
        loadBannerPic();
        loadContentData();
        
    }

    // 记录请求的日期
    private String year = getTodayTime().get(0);
    private String month = getTodayTime().get(1);
    private String day = getTodayTime().get(2);
    
    private void loadContentData() {
        everydayModel.getRecyclerData(new RequestImpl() {
            @Override
            public void loadSuccess(Object obj) {
                
                
                
                showLoadingThis(false);
            }

            @Override
            public void loadFail(Throwable throwable) {
                showLoadingThis(false);
            }

            @Override
            public void addDisposable(Disposable disposable) {
                addBaseDisposable(disposable);
            }
        });
    }

    private void loadBannerPic() {
    }

    /**
     * 获取当天日期
     */
    private ArrayList<String> getTodayTime() {
        String data = TimeUtil.getData();
        String[] split = data.split("-");
        String year = split[0];
        String month = split[1];
        String day = split[2];
        ArrayList<String> list = new ArrayList<>();
        list.add(year);
        list.add(month);
        list.add(day);
        return list;
    }
}
