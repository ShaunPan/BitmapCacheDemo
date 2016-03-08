package com.pan.bitmapcachedemo.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.pan.bitmapcachedemo.R;
import com.pan.bitmapcachedemo.bitmapcache.FileCache;
import com.pan.bitmapcachedemo.bitmapcache.MemoryCache;
import com.pan.bitmapcachedemo.util.Utils;

import java.io.IOException;
import java.io.InputStream;

/*
 * File Name:AsyncImageDownloader
 * Author:Pan
 * Date:2016/3/8 19:09
 * Description:
 */
public class AsyncImageDownloader extends AsyncTask<Void, Void, Bitmap> {

    private static final String TAG = "AsyncImageDownloader";
    private ImageView imageView;
    private String fileName;
    private Context context;

    public AsyncImageDownloader(Context context, ImageView imageView, String fileName) {
        this.context = context;
        this.imageView = imageView;
        this.fileName = fileName;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //在开始异步任务前，执行默认图片操作
        imageView.setBackgroundResource(R.mipmap.ic_launcher);
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        String url = Utils.getRealUrlOfPicture(fileName);
        HttpResponse response = new HttpRetriever().requestGet(url, null);
        Log.i(TAG, "url: " + url);
        Log.i(TAG, "response: " + response);
        InputStream in = null;
        try {
            if (response != null && response.getEntity() != null) {
                in = response.getEntity().getContent();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        //TODO 设置图片的尺寸
        //压缩图片
        Bitmap bitmap = BitmapFactory.decodeStream(in);

        //二级缓存
        new MemoryCache().putBitmap(fileName, bitmap);//使用文件名作为key
        new FileCache(context).put(fileName, bitmap);

        return bitmap;
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap != null && imageView != null) {
            imageView.setImageBitmap(bitmap);
        }

    }


}
