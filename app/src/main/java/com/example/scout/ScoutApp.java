package com.example.scout;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.example.scout.common.lifecycle.Foreground;

/**
 * Created by zhangyuanyuan on 2017/11/13.
 */

public class ScoutApp extends MultiDexApplication {

    private static ScoutApp instance;

    public static ScoutApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        Foreground.init(this);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
