package com.example.jun.zhiliaodemo.util;

import com.orhanobut.logger.Logger;

/**
 * Created by Jun on 2016/7/5.
 */
public class LogUtil {

    // 是否显示Log
    private static boolean isShow = true;

    public static void d(String s) {
        if (isShow) {
            Logger.d(s);
        }
    }

    public static void i(String s) {
        if (isShow) {
            Logger.i(s);
        }
    }

    public static void w(String s) {
        if (isShow) {
            Logger.w(s);
        }
    }

    public static void e(String s) {
        if (isShow) {
            Logger.e(s);
        }
    }

    public static void json(String s) {
        if (isShow) {
            Logger.json(s);
        }
    }

    public static void xml(String s) {
        if (isShow) {
            Logger.xml(s);
        }
    }
}
