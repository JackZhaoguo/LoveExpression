package com.wenzi.loveexpression.utils;

import android.graphics.Bitmap;

public interface ImageCache {
    Bitmap getBitmap(String url);

    void putBitmap(String url, Bitmap bitmap);

    void clearCache();

    void removeBitmap(String url);
}
