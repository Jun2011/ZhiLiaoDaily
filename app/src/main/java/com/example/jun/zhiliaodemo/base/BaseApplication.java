package com.example.jun.zhiliaodemo.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jun on 2016/7/3.
 */
public class BaseApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    // 获取Context
    public static Context getContext() {
        return mContext;
    }
}
