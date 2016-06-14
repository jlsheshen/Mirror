package com.mirroreye.mirror.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by 秦谦谦 on 16/6/13 11:26.
 */
public abstract class BaseActivity extends AutoLayoutActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        initView();
        initData();
    }

    //加载布局的抽象方法
    public abstract int setLayout();

    //加载组件的抽象方法
    protected abstract void initView();

    //加载数据的方法
    protected abstract void initData();

    //组件实例化 不需要转型
    protected <T extends View> T bindView(int id){
        return (T)findViewById(id);
    }
}
