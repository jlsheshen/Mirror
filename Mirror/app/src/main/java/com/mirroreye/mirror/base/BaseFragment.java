package com.mirroreye.mirror.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mirroreye.mirror.R;

/**
 * Created by 秦谦谦 on 16/6/13 11:35.
 */
public abstract class BaseFragment extends Fragment {
    private static Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    //初始化视图
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(setLayout(),container,false);
    }

    public abstract int setLayout();

    //初始化组件|

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    protected abstract void initView(View view);

    //加载数据

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected abstract void initData();


    //初始化组件

    protected <T extends View >T bindView (int id){
        return (T)getView().findViewById(id);
    }
}
