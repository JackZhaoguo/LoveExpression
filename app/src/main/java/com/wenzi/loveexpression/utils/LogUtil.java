package com.wenzi.loveexpression.utils;

import android.util.Log;

public class LogUtil {

    private final static String TAG = "LogUtil";

    private static boolean SHOW_ERR_WNR_LOG = true;

    private static boolean SHOW_INFO_DEBUG_LOG = true;

    private LogUtil() {
    }

    public static String makeLogTag(Class cls) {
        return cls.getSimpleName();
    }

    public static void closeLog() {
        SHOW_INFO_DEBUG_LOG = false;
        SHOW_ERR_WNR_LOG = false;
    }

    public static void i(String msg) {
        if (SHOW_INFO_DEBUG_LOG) {
            Log.i(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (SHOW_INFO_DEBUG_LOG) {
            Log.d(TAG, msg);
        }
    }

    public static void w(Exception ex) {
        if (SHOW_ERR_WNR_LOG) {
            ex.printStackTrace();
        }
    }

    public static void e(String msg) {
        if (SHOW_ERR_WNR_LOG) {
            Log.e(TAG, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (SHOW_INFO_DEBUG_LOG) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (SHOW_INFO_DEBUG_LOG) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (SHOW_ERR_WNR_LOG) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (SHOW_ERR_WNR_LOG) {
            Log.e(tag, msg);
        }
    }

    public static void w(String msg) {
        if (SHOW_ERR_WNR_LOG) {
            Log.w(TAG, msg);
        }
    }
}
