package com.pan.bitmapcachedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pan.bitmapcachedemo.bitmapcache.FileCache;
import com.pan.bitmapcachedemo.bitmapcache.MemoryCache;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new MemoryCache().cleanBitmap();
        new FileCache(this).clean();

    }


}
