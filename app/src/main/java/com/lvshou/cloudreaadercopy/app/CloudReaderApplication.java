package com.lvshou.cloudreaadercopy.app;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.bumptech.glide.Glide;
import com.lvshou.http.HttpUtils;
import com.lvshou.utils.ContextUtils;
import com.lvshou.utils.LogUtil;

/**
 * Created by Lenovo on 2017/11/30.
 */

public class CloudReaderApplication extends Application {


    private static CloudReaderApplication cloudReaderApplication;
    public static CloudReaderApplication getInstance(){
        return cloudReaderApplication;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        cloudReaderApplication = this;
        HttpUtils.getInstance().init(this, LogUtil.LOG_DEBUG);
        ContextUtils.init(this);
        initTextSize();
    }

    private void initTextSize() {
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    @Override
    public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        Glide.with(this).onTrimMemory(level);
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        Glide.with(this).onLowMemory();
    }
}
