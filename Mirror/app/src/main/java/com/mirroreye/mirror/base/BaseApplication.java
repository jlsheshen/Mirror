package com.mirroreye.mirror.base;

import android.app.Application;
import android.content.Context;

import com.mirroreye.mirror.utils.ScreensUtil;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by 秦谦谦 on 16/6/13 16:52.
 */
public class BaseApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        /**
         * 极光推送
         */
        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }

    public static Context getContext() {
        return context;
    }


}
