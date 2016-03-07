package com.pan.bitmapcachedemo.util;

import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/*
 * File Name:BitmapUtil
 * Author:Pan
 * Date:2016/3/7 19:45
 * Description:
 */
public class BitmapUtil {

    /**
     * 保存bitmap至某个路径下
     *
     * @param file   文件
     * @param bitmap 要保存的图片
     * @return true 保存成功；false 保存失败
     */
    public static boolean saveBitmap(File file, Bitmap bitmap) {

        if (file == null || bitmap == null) {
            return false;
        }

        try {
            BufferedOutputStream bitmapOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            return bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bitmapOutputStream);//压缩图片
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
