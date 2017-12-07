package com.lvshou.cloudreaadercopy.utils;

import android.util.Log;
import android.widget.Toast;

import com.lvshou.cloudreaadercopy.app.CloudReaderApplication;


/**
 * Created by liuxiu on 2016/12/14.
 * 单例Toast
 */

public class ToastUtil {

    private static Toast mToast;

    public static void showToast(String text) {
        if (CloudReaderApplication.getInstance() == null)
        if (mToast == null) {
            mToast = Toast.makeText(CloudReaderApplication.getInstance(), text, Toast.LENGTH_SHORT);
        }
        mToast.setText(text);
        mToast.show();
    }
}
