package com.macyer.cloudreaadercopy.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.macyer.cloudreaadercopy.BR;
import com.macyer.cloudreaadercopy.R;
import com.macyer.cloudreaadercopy.app.ConstantsImageUrl;
import com.macyer.cloudreaadercopy.databinding.ActivitySplashBinding;

import java.util.Random;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private SplashEntity splashEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        splashEntity = new SplashEntity(
                ConstantsImageUrl.TRANSITION_URLS[new Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.length)], 
                SplashActivity.this, 
                binding);

        binding.setVariable(BR.splash, splashEntity);

        splashEntity.jump();
    }
}
