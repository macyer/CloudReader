package com.lvshou.cloudreaadercopy.ui;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lvshou.cloudreaadercopy.MainActivity;
import com.lvshou.cloudreaadercopy.R;
import com.lvshou.cloudreaadercopy.databinding.ActivitySplashBinding;

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
        Glide.with(image.getContext())
                .load(ivPic)
                .placeholder(R.mipmap.img_transition_default)
                .error(R.mipmap.img_transition_default)
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
