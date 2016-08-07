package com.example.jun.zhiliaodemo.util;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by Jun on 2016/8/7.
 */
public class ChangeModeUtil {
    // 默认刚开始是白间模式
    public static boolean isNightMode = false;

    public static void setDefaultMode() {
        if (isNightMode) {
            // 设置为夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            // 设置为白间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public static void setLocalMode(AppCompatActivity activity) {
        if (isNightMode) {
            // 设置为夜间模式
            activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            // 设置为白间模式
            activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
