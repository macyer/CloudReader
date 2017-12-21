package com.common.cloudreaadercopy.app;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.bumptech.glide.Glide;
import com.common.http.HttpUtils;
import com.common.utils.ContextUtils;
import com.common.utils.LogUtil;
import com.common.database.AppDatabase;

/**
 * Created by Lenovo on 2017/11/30.
 */

public class CloudReaderApplication extends Application {

    public static AppDatabase database;

    private static CloudReaderApplication cloudReaderApplication;
    public static CloudReaderApplication getInstance(){
        return cloudReaderApplication;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        cloudReaderApplication = this;
        initDataBase();
        HttpUtils.getInstance().init(this, LogUtil.LOG_DEBUG);
        ContextUtils.init(this);
        initTextSize();
    }

    private void initDataBase() {
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-lvshow").build();
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
