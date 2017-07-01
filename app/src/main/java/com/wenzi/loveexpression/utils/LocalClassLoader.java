package com.wenzi.loveexpression.utils;

import android.content.Context;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import dalvik.system.BaseDexClassLoader;

/**
 * Created by suyouxiong on 16-11-8.
 * 用于动态加载新版本jar包
 */
public class LocalClassLoader extends BaseDexClassLoader {
    /**
     * 在该列表中指明使用父classloader中的类
     */
    private static final String[] USE_HOST_CLASS_LIST = {
            "com.meizu.flyme.activeview.views.IActiveView",
    };

    public LocalClassLoader(String dexPath, String optimizedDirectory, String libraryPath, ClassLoader parent) {
        super(dexPath, new File(optimizedDirectory), libraryPath, parent);
    }

    @Override
    protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {

        Class<?> clazz = null;
        boolean useHost = false;
        for (String c : USE_HOST_CLASS_LIST) {
            if (c.equals(className)) useHost = true;
        }
        if (!useHost) {
            try {
                clazz = findClass(className);
            } catch (ClassNotFoundException e) {

            }
        }

        if (clazz == null) {
            clazz = super.loadClass(className, resolve);
        }
        return clazz;
    }

    private static Map<String, LocalClassLoader> sJarClassLoaderCaches = new HashMap<>();

    public static LocalClassLoader createNewActiveLoader(Context context, String jarFilePath) {
        synchronized (sJarClassLoaderCaches) {
            if (sJarClassLoaderCaches.containsKey(jarFilePath)) {
                return sJarClassLoaderCaches.get(jarFilePath);
            }

            try {
                File dexOutPutFile = context.getDir("dex", 0);
                LocalClassLoader dexClassLoader = new LocalClassLoader(jarFilePath, dexOutPutFile.getAbsolutePath(),
                        null, context.getClassLoader());

                sJarClassLoaderCaches.put(jarFilePath, dexClassLoader);
                return dexClassLoader;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static void deleteNewActiveLoader(String jarFilePath) {
        synchronized (sJarClassLoaderCaches) {
            if (sJarClassLoaderCaches.containsKey(jarFilePath)) {
                sJarClassLoaderCaches.remove(jarFilePath);
            }
        }
    }
}
