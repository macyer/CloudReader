package com.macyer.cloudreaadercopy.ui.gank.child;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.macyer.cloudreaadercopy.R;
import com.macyer.cloudreaadercopy.base.BaseFragment;
import com.macyer.cloudreaadercopy.bean.AndroidBean;
import com.macyer.cloudreaadercopy.bean.FrontpageBean.ResultBeanXXXXXXXXXXXXXX.FocusBean.ResultBeanX;
import com.macyer.cloudreaadercopy.databinding.FooterItemEverydayBinding;
import com.macyer.cloudreaadercopy.databinding.FragmentGankEverydayBinding;
import com.macyer.cloudreaadercopy.databinding.HeaderItemEverydayBinding;
import com.macyer.cloudreaadercopy.http.RequestImpl;
import com.macyer.cloudreaadercopy.model.EverydayModel;
import com.macyer.cloudreaadercopy.ui.adapter.EveryDayAdapter;
import com.macyer.cloudreaadercopy.utils.TimeUtil;
import com.macyer.cloudreaadercopy.weiget.banner.BannerImageLoader;
import com.macyer.recyclerview.XRecyclerView;
import com.macyer.recyclerview.util.RecyclerSettings;
import com.macyer.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import fitnessroom.hxs.com.codescan.ScanCodeUtil;
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

    // 记录请求的日期
    private String year = getTodayTime().get(0);
    private String month = getTodayTime().get(1);
    private String day = getTodayTime().get(2);

    private List<List<AndroidBean>> mListsAndroidBean = new ArrayList<>();
    private ArrayList<String> mBannerImages = new ArrayList<>();

    private EveryDayAdapter everyDayAdapter;

    private boolean isFirst = true;

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
        if (mHeaderView == null) {
            mHeaderView = headerBinding.getRoot();
            recyclerView.addHeaderView(mHeaderView);
        }
        if (mFooterView == null) {
            footerBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.footer_item_everyday, null, false);
            mFooterView = footerBinding.getRoot();
            recyclerView.addFootView(mFooterView, true);
            recyclerView.noMoreLoading();
        }
        new RecyclerSettings(recyclerView);
    }

    private void initIds() {
        ll_loading = getView(R.id.ll_loading);
        recyclerView = getView(R.id.xrv_everyday);
        ivLoading = getView(R.id.iv_loading);
        headerBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.header_item_everyday, null, false);
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
     *
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
        if (!isFirst) return;
        showLoadingThis(true);
        everydayModel.setData(getTodayTime().get(0), getTodayTime().get(1), getTodayTime().get(2));
        loadBannerPic();
        loadContentData();

    }

    private void loadContentData() {
        everydayModel.getRecyclerData(new RequestImpl<List<List<AndroidBean>>>() {
            @Override
            public void loadSuccess(List<List<AndroidBean>> lists) {
                if (lists != null && lists.size() > 0) {
                    isFirst = false;
                    if (mListsAndroidBean != null && mListsAndroidBean.size() > 0)
                        mListsAndroidBean.clear();
                    mListsAndroidBean.addAll(lists);
                    setAdapter(mListsAndroidBean);
                    showLoadingThis(false);
                } else {
                    requestBeforeData();
                }
            }

            @Override
            public void loadFail(Throwable throwable) {
                showLoadingThis(false);
            }

            @Override
            public void disposable(Disposable disposable) {
                addBaseDisposable(disposable);
            }
        });
    }

    private void setAdapter(List<List<AndroidBean>> lists) {
        if (everyDayAdapter == null) everyDayAdapter = new EveryDayAdapter();
        else everyDayAdapter.clear();
        everyDayAdapter.addAll(lists);
        recyclerView.setAdapter(everyDayAdapter);
        everyDayAdapter.notifyDataSetChanged();
    }

    /**
     * 没请求到数据就取缓存，没缓存一直请求前一天数据
     */
    private void requestBeforeData() {
        ArrayList<String> lastTime = TimeUtil.getLastTime(year, month, day);
        everydayModel.setData(lastTime.get(0), lastTime.get(1), lastTime.get(2));
        year = lastTime.get(0);
        month = lastTime.get(1);
        day = lastTime.get(2);
        loadContentData();
    }

    private void loadBannerPic() {
        everydayModel.getBannerData(new RequestImpl() {
            @Override
            public void loadSuccess(Object o) {
                mBannerImages.clear();
                ArrayList<ResultBeanX> resultBeanXES = (ArrayList<ResultBeanX>) o;
                for (ResultBeanX resultBeanX:resultBeanXES){
                    mBannerImages.add(resultBeanX.getRandpic());
                }
                
                headerBinding.banner.setImageLoader(new BannerImageLoader());
                headerBinding.banner.isAutoPlay(true);
                headerBinding.banner.setDelayTime(4000);
                headerBinding.banner.setImages(mBannerImages);
                headerBinding.banner.start();

                headerBinding.banner.setOnBannerListener(position -> {
                    // 链接没有做缓存，如果轮播图使用的缓存则点击图片无效
                    if (resultBeanXES.get(position) != null && resultBeanXES.get(position).getCode() != null
                            && resultBeanXES.get(position).getCode().startsWith("http")) {
//                        WebViewActivity.loadUrl(getContext(), mBannerImages.get(position).getCode(), "加载中...");
                    }
                });
            }

            @Override
            public void loadFail(Throwable throwable) {
                LogUtil.e(throwable.getMessage() + throwable.getStackTrace() + throwable.getLocalizedMessage());
            }

            @Override
            public void disposable(Disposable disposable) {
                addBaseDisposable(disposable);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e("====ScanCodeUtil="+ScanCodeUtil.getResultScanCode(resultCode, data));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (null != headerBinding && null != headerBinding.banner)
            headerBinding.banner.startAutoPlay();
    }

    @Override
    protected void onInVisible() {
        super.onInVisible();
        if (null != headerBinding && null != headerBinding.banner)
            headerBinding.banner.stopAutoPlay();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 失去焦点，否则RecyclerView第一个item会回到顶部
        bindingView.xrvEveryday.setFocusable(false);
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
