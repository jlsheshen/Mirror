package com.mirroreye.mirror.base;

import android.app.Application;
import android.content.Context;

import com.mirroreye.mirror.utils.ScreensUtil;

/**
 * Created by 秦谦谦 on 16/6/13 16:52.
 */
public class BaseApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }

    public static Context getContext() {
        return context;
    }
}
