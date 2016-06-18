package com.mirroreye.mirror.ui.main;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.adapter.PPWAdapter;
import com.mirroreye.mirror.base.BaseFragment;
import com.mirroreye.mirror.interfaces.PPWToFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liangduo on 16/6/14.
 */
public class CartFragment extends BaseFragment {
    private LinearLayout cartFragmentClassify;
    //pop
    private PopupWindow popupWindow;
    private View view;
    private ListView ppwListView;
    private PPWAdapter ppwAdapter;
    private List<String> data;
    private PPWToFragment ppwToFragment;

    @Override
    public int setLayout() {
        return R.layout.fragment_cart;
    }

    @Override
    protected void initView(View view) {
        cartFragmentClassify = bindView(R.id.cart_fragment_classify);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ppwToFragment= (PPWToFragment) context;
    }

    @Override
    protected void initData() {

        //popupWindow
        //设置抽屉
        popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view = LayoutInflater.from(getContext()).inflate(R.layout.ppw_info, null);
        //初始化抽屉的组件
        ppwListView = (ListView) view.findViewById(R.id.ppw_list);
        popupWindow.setContentView(view);
        cartFragmentClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToppp();
            }
        });

        ppwListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setTouchable(true);
                        Log.d("AllFragment", "position:" + position);

                        ppwToFragment.getPosition(position);
                        popupWindow.dismiss();
                    }
                }
        );

    }

    private void goToppp() {


        //点击事件弹出抽屉

        //抽屉的弹出
        popupWindow.showAtLocation(view, Gravity.TOP, 0, 0);
        //popupWindow的平移
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_PARENT, 1,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_PARENT, 1
        );
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(200);
        ppwListView.startAnimation(translateAnimation);
        //popupWindow的缩放
        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0.9f, 1, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setFillAfter(true);//设置动画停止的位置

        ppwListView.startAnimation(scaleAnimation);
        data = new ArrayList<>();
        data.add("瀏覽所有分類");
        data.add("瀏覽平光眼鏡");
        data.add("瀏覽太陽眼鏡");
        data.add("專題分享");
        data.add("我的購物車");
        data.add("返回首頁");
        data.add("退出");
        ppwAdapter = new PPWAdapter(getContext());
        ppwAdapter.setData(data);
        ppwListView.setAdapter(ppwAdapter);
    }
}
