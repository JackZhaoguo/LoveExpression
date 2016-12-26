package com.wenzi.loveexpression.utils;


import android.os.AsyncTask;
import android.os.SystemClock;

import com.alibaba.fastjson.JSON;

public class JsonParser {
    private static final String LOG_TAG = "activeview.JsonParser";

    public static <T> T parseJson(String jsonStr, Class<T> tClass) {
        if (jsonStr.isEmpty()) {
            LogUtil.e(LOG_TAG, "jsonStr to parse is NULL!");
            return null;
        }

        T parseResult;
        try {
            LogUtil.i(LOG_TAG, "jsonStr ="+ jsonStr);

            long start = SystemClock.currentThreadTimeMillis();
            parseResult = JSON.parseObject(jsonStr, tClass);
            long end = SystemClock.currentThreadTimeMillis();

            LogUtil.e(LOG_TAG, "parse jsonStr.length=" + jsonStr.length() + ", UseTime =" + String.valueOf(end - start));
        } catch (Exception e) {
            LogUtil.e(LOG_TAG, "Exception e=" + e.getMessage());
            return null;
        }
        return parseResult;
    }

    public static <T> AsyncTask parseJsonAsync(String jsonStr, Class<T> tClass, OnJsonParserListener listener) {
        if (jsonStr.isEmpty()) {
            LogUtil.e(LOG_TAG, "jsonStr to parse is NULL!");
            return null;
        }

        ParseJsonTask task = new ParseJsonTask(jsonStr, tClass, listener);
        task.execute();
        return task;
    }
}
