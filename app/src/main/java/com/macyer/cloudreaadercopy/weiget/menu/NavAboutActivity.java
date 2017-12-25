package com.macyer.cloudreaadercopy.weiget.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.macyer.arouter.RouterPath;
import com.macyer.cloudreaadercopy.R;
import com.macyer.cloudreaadercopy.base.BaseActivity;
import com.macyer.utils.ToastUtil;

/**
 * Created by Lenovo on 2017/12/18.
 */

@Route(path = RouterPath.about)
public class NavAboutActivity extends BaseActivity {

    @Autowired
    String name;
    
    public static void start(Context context) {
        context.startActivity(new Intent(context, NavAboutActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_about);
        ARouter.getInstance().inject(this);
        ToastUtil.show(name);
        showLoading();
//        showContentView();
    }
}
