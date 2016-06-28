package com.mirroreye.mirror.ui.welcome;

import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.base.BaseActivity;
import com.mirroreye.mirror.ui.main.MainActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 秦谦谦 on 16/6/13 11:26.
 */
public class WelcomeActivity extends BaseActivity {
    private ImageView imageView;
    private CountDownTimer timer;//倒计时欢迎页
    @Override
    public int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        imageView=bindView(R.id.welcome_image);
    }

    @Override
    protected void initData() {

        timer=new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
            //5秒之后进行页面的跳转
                Intent intent=new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}
