package com.pan.bitmapcachedemo.bitmapcache;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.pan.bitmapcachedemo.Config;
import com.pan.bitmapcachedemo.util.BitmapUtil;

import java.io.File;
import java.io.IOException;

/*
 * File Name:FileCache
 * Author:Pan
 * Date:2016/3/7 20:16
 * Description:外部存储缓存bitmap
 */
public class FileCache {

    private static final String FILE_CACHE_TAG = "FileCache";

    //存储bitmap的路径
    private File cacheDir;

    /**
     * 构造函数
     *
     * @param context Context
     */
    public FileCache(Context context) {

        //检测外部存储是否可用，如果可用则获取外部存储路径
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //创建bitmap的存储路径
            cacheDir = new File(Environment.getExternalStorageDirectory(), Config.CACHE_DIR);
        } else {
            //如果外部存储不可用，则获取内部存储路径进行存储
            cacheDir = context.getCacheDir();
        }

        //如果路径不存在，则创建路径
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        Log.i(FILE_CACHE_TAG, "Cache dir" + cacheDir.getAbsolutePath());
    }

    public void put(String key, Bitmap bitmap) {
        //创建对应的file
        File file = new File(cacheDir, key);
        //判断该file是否存在，如果不存在，则创建一个新的file
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //存储bitmap
        if (BitmapUtil.saveBitmap(file, bitmap)) {
            Log.i(FILE_CACHE_TAG, "Bitmap saved to sdcard successfully");
        } else {
            Log.e(FILE_CACHE_TAG, "Bitmap saved to sdcard failed");
        }

    }

    /**
     * 根据key值获取对应的File
     * @param key
     * @return
     */
    public File getFile(String key) {
        File file = new File(cacheDir, key);
        if (file.exists()) {
            Log.i(FILE_CACHE_TAG, "the file you wanted exists " + file.getAbsolutePath());
            return file;
        } else {
            Log.i(FILE_CACHE_TAG, "the file you wanted does not exists " + file.getAbsolutePath());
        }

        return null;
    }

    /**
     * 清空sdcard中存储的图片
     */
    public void clean() {
        File[] files = cacheDir.listFiles();
        for (File file : files) {
            file.delete();
        }
    }
}
