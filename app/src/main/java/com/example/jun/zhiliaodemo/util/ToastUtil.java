package com.example.jun.zhiliaodemo.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Jun on 2016/7/30.
 */
public class ToastUtil {

    private static Toast mToast;

    // String
    public static void showToast(Context context, String str, int duration) {
        if (mToast == null)
            mToast = Toast.makeText(context, str, duration);
        else
            mToast.setText(str);
        mToast.show();
    }

    // CharSequence
    public static void showToast(Context context, CharSequence str, int duration) {
        if (mToast == null)
            mToast = Toast.makeText(context, str, duration);
        else
            mToast.setText(str);
        mToast.show();
    }
}
