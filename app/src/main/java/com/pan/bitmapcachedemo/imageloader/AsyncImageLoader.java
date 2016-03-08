package com.pan.bitmapcachedemo.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.pan.bitmapcachedemo.bitmapcache.FileCache;
import com.pan.bitmapcachedemo.bitmapcache.MemoryCache;
import com.pan.bitmapcachedemo.util.BitmapUtil;

import java.io.File;

/*
 * File Name:AsyncImageLoader
 * Author:Pan
 * Date:2016/3/8 19:30
 * Description:
 */
public class AsyncImageLoader {

    private static final String TAG = "AsyncImageLoader";

    private Context context;
    private MemoryCache memoryCache;
    private FileCache fileCache;

    private static AsyncImageLoader imageLoader;

    private AsyncImageLoader(Context context) {
        this.context = context;
        this.memoryCache = new MemoryCache();
        this.fileCache = new FileCache(context);
    }

    /*单例模式，保证ImageLoader唯一*/
    public static AsyncImageLoader getInstance(Context context) {
        if (imageLoader == null) {
            imageLoader = new AsyncImageLoader(context);
        }
        return imageLoader;
    }

    /**
     * 展示图片
     *
     * @param imageView 展示图片的ImageView
     * @param fileName  图片名称
     */
    public void displayBitmap(ImageView imageView, String fileName) {

        //排除图片为空的可能
        if (fileName == null && "".equals(fileName)) {
            return;
        }

        Bitmap bitmap = getBitmap(fileName);
        //当bitmap不为空时，直接显示图片，
        //否则从服务器重新下载
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            new AsyncImageDownloader(context, imageView, fileName).execute();
        }
    }

    /**
     * 获取bitmap：先检索内存，如果不存在，再检索外部存储
     * @param key 缓存中对应的key
     * @return bitmap
     */
    private Bitmap getBitmap(String key) {
        Bitmap bitmap = null;
        //先搜索内存中是否存在该图片
        bitmap = memoryCache.getBitmap(key);

        //如果内存中不存在，搜索sd卡中是否存在该图片
        if (bitmap == null) {
            File file = fileCache.getFile(key);

            //图片参数配置项
            BitmapFactory.Options options = new BitmapFactory.Options();
            boolean inJustDecodeBounds = options.inJustDecodeBounds;
            bitmap = BitmapUtil.decodeFile(file, null);
        }
        return bitmap;
    }

    /**
     * 清空缓存
     */
    public void cleanCache() {
        if (memoryCache != null) {
            memoryCache.cleanBitmap();
        }
        if (fileCache != null) {
            fileCache.clean();
        }
    }


}
