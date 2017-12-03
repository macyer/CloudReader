package com.lvshou.cloudreaadercopy.app;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.lvshou.cloudreaadercopy.utils.DebugUtil;
import com.lvshou.http.HttpUtils;

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
        HttpUtils.getInstance().init(this, DebugUtil.DEBUG);
        
        initTextSize();
    }

    private void initTextSize() {
        Resources res = getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
    }
}
