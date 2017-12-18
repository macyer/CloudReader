package com.lvshou.cloudreaadercopy.weiget.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.lvshou.cloudreaadercopy.R;
import com.lvshou.cloudreaadercopy.base.BaseActivity;

/**
 * Created by Lenovo on 2017/12/18.
 */

public class NavAboutActivity extends BaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, NavAboutActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_about);
        showLoading();
//        showContentView();
    }
}
