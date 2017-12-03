package com.lvshou.cloudreaadercopy.ui.gank;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.lvshou.cloudreaadercopy.R;
import com.lvshou.cloudreaadercopy.base.BaseFragment;
import com.lvshou.cloudreaadercopy.base.MyViewpagerAdapter;
import com.lvshou.cloudreaadercopy.databinding.FragmentGankBinding;
import com.lvshou.cloudreaadercopy.ui.gank.child.AndroidFragment;
import com.lvshou.cloudreaadercopy.ui.gank.child.CustomFragment;
import com.lvshou.cloudreaadercopy.ui.gank.child.EverydayFragment;
import com.lvshou.cloudreaadercopy.ui.gank.child.WelfareFragment;

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

        MyViewpagerAdapter myViewpagerAdapter = new MyViewpagerAdapter(getChildFragmentManager(),mFragments,mTitleList);
        TabLayout tabLayout = getView(R.id.tab_gank);
        ViewPager viewPager = getView(R.id.vp_gank);
        viewPager.setAdapter(myViewpagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
        myViewpagerAdapter.notifyDataSetChanged();
    }
}
