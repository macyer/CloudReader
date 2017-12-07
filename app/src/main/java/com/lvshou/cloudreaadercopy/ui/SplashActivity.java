package com.lvshou.cloudreaadercopy.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lvshou.cloudreaadercopy.BR;
import com.lvshou.cloudreaadercopy.R;
import com.lvshou.cloudreaadercopy.app.ConstantsImageUrl;
import com.lvshou.cloudreaadercopy.databinding.ActivitySplashBinding;

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
