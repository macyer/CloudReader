package com.macyer.cloudreaadercopy;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.macyer.cloudreaadercopy.app.ConstantsImageUrl;
import com.macyer.cloudreaadercopy.base.BaseFragmentPagerAdapter;
import com.macyer.cloudreaadercopy.clickListner.MainCLickListner;
import com.macyer.cloudreaadercopy.databinding.ActivityMainBinding;
import com.macyer.cloudreaadercopy.databinding.NavHeaderMainBinding;
import com.macyer.cloudreaadercopy.ui.book.BookFragment;
import com.macyer.cloudreaadercopy.ui.gank.GankFragment;
import com.macyer.cloudreaadercopy.ui.one.OneFragment;
import com.macyer.cloudreaadercopy.utils.ImgLoadUtil;
import com.macyer.cloudreaadercopy.weiget.menu.NavAboutActivity;
import com.macyer.utils.PerfectClickListener;
import com.macyer.utils.ToastUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainCLickListner, ViewPager.OnPageChangeListener {

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private ActivityMainBinding binding;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setListner(this);
        initIds();
                
        initToolbar();

        initDrawer();

        initNavigat();
        
        initContentFragment();
    }

    private void initContentFragment() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new GankFragment());
        fragments.add(new OneFragment());
        fragments.add(new BookFragment());

        viewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(),fragments));
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
        binding.include.ivTitleGank.setSelected(true);
    }
    
    

    private void initIds() {
        drawer = binding.drawerLayout;
        navigationView = binding.navView;
        toolbar = binding.include.toolbar;
        viewPager = binding.include.includeContent;
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void initDrawer() {
        //系统自带 带有动画
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
    }

    NavHeaderMainBinding bindingNav;

    private void initNavigat() {
        navigationView.inflateHeaderView(R.layout.nav_header_main);
        View view = navigationView.getHeaderView(0);
        bindingNav = DataBindingUtil.bind(view);
//        bindingNav.setListener(this);
        bindingNav.ivAvatar.setOnClickListener(listener);

        ImgLoadUtil.displayCircle(bindingNav.ivAvatar, ConstantsImageUrl.IC_AVATAR);
        bindingNav.llNavHomepage.setOnClickListener(listener);
        bindingNav.llNavScanDownload.setOnClickListener(listener);
        bindingNav.llNavDeedback.setOnClickListener(listener);
        bindingNav.llNavAbout.setOnClickListener(listener);
        bindingNav.llNavLogin.setOnClickListener(listener);
        bindingNav.llNavExit.setOnClickListener(listener);


    }

    private PerfectClickListener listener = new PerfectClickListener() {

        @Override
        protected void onNoDoubleClick(View v) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            binding.drawerLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    switch (v.getId()) {
                        case R.id.iv_avatar:
                            ToastUtil.show("头像");
                            break;
                        case R.id.ll_nav_deedback:
                            ToastUtil.show("问题反馈");
                            break;
                        case R.id.ll_nav_exit:
                            ToastUtil.show("退出应用");
                            break;
                        case R.id.ll_nav_homepage:
                            ToastUtil.show("项目主页");
                            break;
                        case R.id.ll_nav_login:
                            ToastUtil.show("登录Git");
                            break;
                        case R.id.ll_nav_about:
                            NavAboutActivity.start(MainActivity.this);
                            break;
                        case R.id.ll_nav_scan_download:
                            ToastUtil.show("扫码下载");
                            break;
                    }
                }
            }, 260);
        }
    };

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                moveTaskToBack(true);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            ToastUtil.show("search");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onNightModeClick(View view) {

    }

    public boolean getNightMode(View view) {
        return false;
    }

    @Override
    public void onClickMain(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.ll_title_menu:
                drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.iv_title_gank:
                if (viewPager.getCurrentItem() != 0){
                    binding.include.ivTitleGank.setSelected(true);
                    binding.include.ivTitleOne.setSelected(false);
                    binding.include.ivTitleDou.setSelected(false);
                    viewPager.setCurrentItem(0);
                }
                break;
            case R.id.iv_title_one:
                if (viewPager.getCurrentItem() != 1){
                    binding.include.ivTitleGank.setSelected(false);
                    binding.include.ivTitleOne.setSelected(true);
                    binding.include.ivTitleDou.setSelected(false);
                    viewPager.setCurrentItem(1);
                }
                break;
            case R.id.iv_title_dou:
                if (viewPager.getCurrentItem() != 2){
                    binding.include.ivTitleGank.setSelected(false);
                    binding.include.ivTitleOne.setSelected(false);
                    binding.include.ivTitleDou.setSelected(true);
                    viewPager.setCurrentItem(2);
                }
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        
    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                binding.include.ivTitleGank.setSelected(true);
                binding.include.ivTitleOne.setSelected(false);
                binding.include.ivTitleDou.setSelected(false);
                break;
            case 1:
                binding.include.ivTitleGank.setSelected(false);
                binding.include.ivTitleOne.setSelected(true);
                binding.include.ivTitleDou.setSelected(false);
                break;
            case 2:
                binding.include.ivTitleGank.setSelected(false);
                binding.include.ivTitleOne.setSelected(false);
                binding.include.ivTitleDou.setSelected(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
