package com.chrissen.zhitian;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;

import org.litepal.LitePal;

/**
 * Created by Administrator on 2017/8/20 0020.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        context = getApplicationContext();
        LeakCanary.install(this);
    }

    public static Context getContext(){
        return context;
    }

}
