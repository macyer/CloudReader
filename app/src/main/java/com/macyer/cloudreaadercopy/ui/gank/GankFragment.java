package com.macyer.cloudreaadercopy.ui.gank;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.macyer.cloudreaadercopy.R;
import com.macyer.cloudreaadercopy.base.BaseFragment;
import com.macyer.cloudreaadercopy.base.BaseFragmentPagerAdapter;
import com.macyer.cloudreaadercopy.databinding.FragmentGankBinding;
import com.macyer.cloudreaadercopy.ui.gank.child.AndroidFragment;
import com.macyer.cloudreaadercopy.ui.gank.child.CustomFragment;
import com.macyer.cloudreaadercopy.ui.gank.child.EverydayFragment;
import com.macyer.cloudreaadercopy.ui.gank.child.WelfareFragment;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2017/12/1.
 */

public class GankFragment extends BaseFragment<FragmentGankBinding>{
    
    private ArrayList<String> mTitleList = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    
    @Override
    public int setContentView() {
        return R.layout.fragment_gank;
    }

    @Override
    protected void initView() {
        initFragmentList();
    }

    @Override
    public void loadData() {

    }


    private void initFragmentList() {
        mTitleList.add("每日推荐");
        mTitleList.add("福利");
        mTitleList.add("干货订制");
        mTitleList.add("大安卓");
        mFragments.add(new EverydayFragment());
        mFragments.add(new WelfareFragment());
        mFragments.add(new CustomFragment());
        mFragments.add(AndroidFragment.newInstance("Android"));

        BaseFragmentPagerAdapter myViewpagerAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager(),mFragments,mTitleList);
        TabLayout tabLayout = getView(R.id.tab_gank);
        ViewPager viewPager = getView(R.id.vp_gank);
        viewPager.setAdapter(myViewpagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
        myViewpagerAdapter.notifyDataSetChanged();
    }
}
