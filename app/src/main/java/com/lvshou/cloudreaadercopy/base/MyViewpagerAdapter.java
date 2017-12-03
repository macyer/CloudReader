package com.lvshou.cloudreaadercopy.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2017/12/1.
 */

public class MyViewpagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> titleList;

    public MyViewpagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public MyViewpagerAdapter(FragmentManager fm,ArrayList<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    public MyViewpagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList, ArrayList<String> titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titleList != null && titleList.size()>0){
            return titleList.get(position);
        }else  return "";
    }
}
