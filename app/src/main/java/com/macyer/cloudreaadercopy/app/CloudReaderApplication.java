package com.macyer.cloudreaadercopy.app;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Debug;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.macyer.http.HttpUtils;
import com.macyer.utils.BuildConfig;
import com.macyer.utils.ContextUtils;
import com.macyer.utils.LogUtil;
import com.macyer.database.AppDatabase;

/**
 * Created by Lenovo on 2017/11/30.
 */

public class CloudReaderApplication extends Application {

    public static AppDatabase database;

    private static CloudReaderApplication cloudReaderApplication;

    public static CloudReaderApplication getInstance() {
        return cloudReaderApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        cloudReaderApplication = this;
        initArouter();
        initDataBase();
        HttpUtils.getInstance().init(this, LogUtil.LOG_DEBUG);
        ContextUtils.init(this);
        initTextSize();
    }

    /*
    初始化Arouter
    */
    private void initArouter() {
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
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
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.with(this).onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.with(this).onLowMemory();
    }
}
