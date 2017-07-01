package com.wenzi.loveexpression.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;

/**
 * Created by meizu on 16/10/20.
 */
public class PreferenceUtils {

    private static String SP_NAME = "activecache";

    private Context mContext;
    private static SharedPreferences mSharedPreferences;

    private volatile static PreferenceUtils mPreferenceUtilsInstance;

    public static PreferenceUtils getInstance(Context ctx) {
        if (mPreferenceUtilsInstance == null) {
            synchronized (PreferenceUtils.class) {
                if (mPreferenceUtilsInstance == null) {
                    mPreferenceUtilsInstance = new PreferenceUtils(ctx);
                }
            }
        }
        return mPreferenceUtilsInstance;
    }

    private PreferenceUtils(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public boolean isCached(String url) {
        return mSharedPreferences != null && mSharedPreferences.contains(url);
    }

    public void saveToSharedPreferences(String key, String value) {
        if (mSharedPreferences != null) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(key, value);
            editor.apply();
        }
    }

    public void removeSharedPreference(String key) {
        if (mSharedPreferences != null) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.remove(key);
            editor.apply();
        }
    }

    public String getSharePreferenceValue(String key) {
        return mSharedPreferences != null ? mSharedPreferences.getString(key, "") : null;
    }
}
