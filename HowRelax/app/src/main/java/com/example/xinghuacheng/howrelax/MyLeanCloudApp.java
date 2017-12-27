package com.example.xinghuacheng.howrelax;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Xinghua Cheng on 16/06/2017.
 */

public class MyLeanCloudApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"tFoNw2q6aoyEfUKTXUl3wS3q-gzGzoHsz","Hlkt9S428ir5nBFeOqOdk53b");
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true);
    }

}
