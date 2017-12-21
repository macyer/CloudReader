package com.common.cloudreaadercopy.ui.gank.child;

import android.os.Bundle;

import com.common.cloudreaadercopy.R;
import com.common.cloudreaadercopy.base.BaseFragment;
import com.common.cloudreaadercopy.databinding.FragmentGankAndroidBinding;

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
