package com.eternal.express.app;

import android.app.Application;

import io.realm.Realm;

/**
 * @author qiuyongheng
 * @time 2017/5/8  13:35
 * @desc ${TODD}
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化Realm内存数据库
        Realm.init(this);
    }
}
