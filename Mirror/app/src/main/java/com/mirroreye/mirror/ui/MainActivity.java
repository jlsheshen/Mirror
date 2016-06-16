package com.mirroreye.mirror.ui;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.adapter.PPWAdapter;
import com.mirroreye.mirror.base.BaseActivity;
import com.mirroreye.mirror.listener.L;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private TextView textView;
    private PopupWindow popupWindow;
    private View view;
    private ListView ppwListView;
    private PPWAdapter ppwAdapter;
    private List<String> data;


    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        textView = bindView(R.id.test);
    }

    @Override
    protected void initData() {
        //设置抽屉
        popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view = LayoutInflater.from(this).inflate(R.layout.ppw_info, null);
        //初始化抽屉的组件
        ppwListView = (ListView) view.findViewById(R.id.ppw_list);

        popupWindow.setContentView(view);
        //点击事件弹出抽屉
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //抽屉的弹出
                popupWindow.showAtLocation(view, Gravity.TOP, 0, 0);
                data = new ArrayList<>();
                data.add("瀏覽所有分類");
                data.add("瀏覽平光眼鏡");
                data.add("瀏覽太陽眼鏡");
                data.add("專題分享");
                data.add("我的購物車");
                data.add("返回首頁");
                data.add("退出");
                ppwAdapter = new PPWAdapter(MainActivity.this);
                ppwAdapter.setData(data);
                ppwListView.setAdapter(ppwAdapter);

            }
        });

        if (popupWindow.isShowing()) {
           view.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Log.d("MainActivity", "点击view");
                  popupWindow.dismiss();
               }
           });
        }
    }


}
