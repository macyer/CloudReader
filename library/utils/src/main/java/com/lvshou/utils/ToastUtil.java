package com.lvshou.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by liuxiu on 2016/8/26.
 */
public class ToastUtil {

    private static Toast toast;
    private static Context context;

    public static void show(@StringRes int des) {
        context = ContextUtils.getContext();
        show(context.getResources().getString(des));
    }

    public static void show(@NonNull String des) {
        if (toast == null) {
            toast = Toast.makeText(context, des, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            LinearLayout toastView = (LinearLayout) toast.getView();
            ImageView imageCodeProject = new ImageView(context.getApplicationContext());
            imageCodeProject.setImageResource(R.drawable.toast_icon);
            toastView.addView(imageCodeProject, 0);
        }
        toast.setText(des);
        toast.show();
    }
}
