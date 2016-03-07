package com.pan.bitmapcachedemo.bitmapcache;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.WeakHashMap;

/*
 * File Name:MemoryCache
 * Author:Pan
 * Date:2016/3/7 19:56
 * Description:内存中缓存bitmap
 */
public class MemoryCache {

    private static final String MEMORY_CACHE_TAG = "MemoryCache";

    //在内存中开辟空间，用于临时存储bitmap
    //使用弱引用的好处是，当该引用不再被使用时，GC会自动将其回收
    private WeakHashMap<String, Bitmap> memoryCache = new WeakHashMap<String, Bitmap>();

    /**
     * 将bitmap存入内存空间
     *
     * @param key    对应的key
     * @param bitmap 要存储的bitmap
     */
    public void putBitmap(String key, Bitmap bitmap) {

        if (key != null && !(key.equals("")) && bitmap != null) {
            //将bitmap保存到内存中
            memoryCache.put(key, bitmap);

            Log.i(MEMORY_CACHE_TAG, "Size of memory cache:" + memoryCache.size());
        }
    }

    /**
     * 根据key的值获取对应的bitmap
     *
     * @param key 指定的key值
     * @return 返回的bitmap
     */
    public Bitmap getBitmap(String key) {
        if (key != null && !key.equals("")) {
            return memoryCache.get(key);
        }

        return null;
    }

    /**
     * 清空缓存的bitmap
     */
    public void cleanBitmap() {
        memoryCache.clear();
        Log.i(MEMORY_CACHE_TAG, "All bitmap is cleaned!");
    }
}
