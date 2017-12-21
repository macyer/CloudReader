package com.macyer.cloudreaadercopy.ui;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.macyer.cloudreaadercopy.MainActivity;
import com.macyer.cloudreaadercopy.R;
import com.macyer.cloudreaadercopy.databinding.ActivitySplashBinding;
import com.macyer.glideimageview.progress.GlideApp;

/**
 * Created by Lenovo on 2017/11/30.
 */

public class SplashEntity {

    private String ivPic;
    private boolean isIn;
    private Context context;
    private  ActivitySplashBinding binding;
    private android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    binding.ivDefault.setVisibility(View.GONE);
                    break;
                case 2:
                    toMain(context);
                    break;
            }

        }
    };

    public SplashEntity(String ivPic, Context context, ActivitySplashBinding binding) {
        this.ivPic = ivPic;
        this.context =context;
        this.binding =binding;
    }

    @BindingAdapter("splash_iv_pic")
    public static void setImage(ImageView image, String ivPic) {
        GlideApp.with(image)
                .load(ivPic)
                .placeholder(R.mipmap.img_transition_default)
                .error(R.mipmap.img_transition_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
    }


    public void toMain(View view) {
        toMain(view.getContext());
    }

    public void toMain(Context context) {
        if (isIn) {
            return;
        }
        MainActivity.start(context);
        ((SplashActivity)context).overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        ((SplashActivity)context).finish();
        isIn = true;
    }
    
    public void jump(){
        mHandler.sendEmptyMessageDelayed(1, 1500);
        mHandler.sendEmptyMessageDelayed(2, 3500);
    }

    public String getIvPic() {
        return ivPic;
    }

    public void setIvPic(String ivPic) {
        this.ivPic = ivPic;
    }

    public boolean isIn() {
        return isIn;
    }

    public void setIn(boolean in) {
        isIn = in;
    }
}
