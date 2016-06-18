package com.mirroreye.mirror.utils;

import android.content.Context;

import com.mirroreye.mirror.base.BaseApplication;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by 秦谦谦 on 16/6/18 16:26.
 *
 */
public class ThreeLogin {
    private Context context;

    public ThreeLogin() {
    }

    public ThreeLogin(Context context) {
        this.context = context;
    }

    public void weiBo(){
        ShareSDK.initSDK(BaseApplication.getContext());
        final Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
        weibo.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                //完成之后
                weibo.getDb().getUserName();
                weibo.getDb().getUserIcon();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        weibo.authorize();

    }
    public void qq(){
        ShareSDK.initSDK(BaseApplication.getContext());
        final Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                //登录成功时调用
                qq.getDb().getUserName();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        qq.showUser(null);
    }
}
