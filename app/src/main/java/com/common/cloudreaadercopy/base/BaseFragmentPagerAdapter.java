package com.common.cloudreaadercopy.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2017/12/1.
 */

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> titleList;

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * 没有title,只有fragment
     * @param fm FragmentManager
     * @param fragmentList 
     */
    public BaseFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    /**
     * title-fragment全部都有
     * @param fm FragmentManager
     * @param fragmentList
     * @param titleList
     */
    public BaseFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList, ArrayList<String> titleList) {
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
