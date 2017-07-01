package com.wenzi.loveexpression.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

// 实现bitmap内存缓存
public class ImageCacheUtils {

    private ImageCache mImageCache;

    private volatile static ImageCacheUtils imageCacheUtilsInstance;

    public static ImageCacheUtils getInstance() {
        if (imageCacheUtilsInstance == null) {
            synchronized (ImageCacheUtils.class) {
                if (imageCacheUtilsInstance == null) {
                    imageCacheUtilsInstance = new ImageCacheUtils();
                }
            }
        }
        return imageCacheUtilsInstance;
    }

    private ImageCacheUtils() {
    }

    /**
     * 使用外部设置的Cache
     *
     * @param cache    外部实现的缓存.需要实现ImageCache接口
     */
    public void setImageCache(ImageCache cache) {
        if (mImageCache != null) {
            mImageCache.clearCache();
        }
        mImageCache = cache;
    }

    private ImageCache getCache() {
        if (mImageCache == null) {
            mImageCache = new BitmapCache();
        }
        return mImageCache;
    }

    /**
     * 往内存缓存中添加Bitmap
     *
     * @param url
     * @param bitmap
     */
    public void putBitmapToImageCache(String url, Bitmap bitmap) {
        getCache().putBitmap(url, bitmap);
    }

    /**
     * 根据key来获取内存中的图片
     * @param url
     * @return
     */
    public Bitmap getBitmapFromImageCache(String url) {
        Bitmap bitmap = getCache().getBitmap(url);
        if(bitmap != null && bitmap.isRecycled()) {
            bitmap = null;
        }
        return bitmap;
    }

    /**
     * 从cache中移除url指定的图片
     * @param url
     * @return
     */
    public void removeBitmapFromCache(String url) {
        getCache().removeBitmap(url);
    }

    public void clearCache() {
        getCache().clearCache();
        mImageCache = null;
    }

    public class BitmapCache implements ImageCache {
        private LruCache<String, Bitmap> mMemoryCache;

        public BitmapCache() {
            //获取应用程序的最大内存
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory());

            //用最大内存的1/4来存储图片
            final int cacheSize = maxMemory / 4;

            mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                //获取每张图片的大小
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mMemoryCache.get(Md5Helper.MD5Encode(url));
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            String hashKey = Md5Helper.MD5Encode(url);
            if (getBitmapFromImageCache(hashKey) == null && bitmap != null) {
                mMemoryCache.put(hashKey, bitmap);
            }
        }

        @Override
        public void removeBitmap(String url) {                      // 只需要从cache中移除, 不需要手动recycle.
            if (null != mMemoryCache) {
                mMemoryCache.remove(Md5Helper.MD5Encode(url));
            }
        }

        @Override
        public void clearCache() {
            if (mMemoryCache != null) {
                if (mMemoryCache.size() > 0) {
                    mMemoryCache.evictAll();
                }
            }
        }
    }
}
