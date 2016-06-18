package com.mirroreye.mirror.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.mirroreye.mirror.base.BaseApplication;

/**
 * 屏幕辅助类
 * Created by ${jl} on 16/6/14.
 */
public class ScreensUtil {

    public static DisplayMetrics getScreenMetrics(){
        WindowManager windowManager = (WindowManager) BaseApplication.getContext().getSystemService(Context.CAMERA_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }
    public static int getScreenWidth(){

        return getScreenMetrics().widthPixels;
    }
    public static int getScreenHeight()
    {
        WindowManager wm = (WindowManager) BaseApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

}
