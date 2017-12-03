package com.lvshou.cloudreaadercopy.ui.gank.child;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.lvshou.cloudreaadercopy.R;
import com.lvshou.cloudreaadercopy.base.BaseFragment;
import com.lvshou.cloudreaadercopy.databinding.FragmentGankAndroidBinding;
import com.lvshou.cloudreaadercopy.databinding.FragmentGankBinding;

/**
 * Created by Lenovo on 2017/12/1.
 */

public class AndroidFragment extends BaseFragment<FragmentGankAndroidBinding> {

    private static final String TAG = "AndroidFragment";
    private static final String TYPE = "mType";
    
    @Override
    public int setContentView() {
        return R.layout.fragment_gank_android;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void loadData() {

    }

    public AndroidFragment() {
    }

    public static AndroidFragment newInstance(String android) {
        AndroidFragment fragment = new AndroidFragment();
        Bundle bundle = new Bundle();
        bundle.putString("",android);
        fragment.setArguments(bundle);
        return fragment;       
    }
}
