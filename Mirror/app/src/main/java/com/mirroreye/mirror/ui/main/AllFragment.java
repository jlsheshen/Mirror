package com.mirroreye.mirror.ui.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
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
import android.widget.TextView;
import android.widget.Toast;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.adapter.HorizontalRecyclerViewAdapter;
import com.mirroreye.mirror.adapter.PPWAdapter;
import com.mirroreye.mirror.base.BaseFragment;
import com.mirroreye.mirror.bean.PPWType;
import com.mirroreye.mirror.interfaces.PPWToFragment;
import com.mirroreye.mirror.ui.goods.GoodsDetails;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import de.greenrobot.event.EventBus;


/**
 * Created by liangduo on 16/6/14.
 */
public class AllFragment extends BaseFragment implements HorizontalRecyclerViewAdapter.MyOnItemClickListener {


    private TextView textView;
    private PopupWindow popupWindow;
    private View view;
    private ListView ppwListView;
    private PPWAdapter ppwAdapter;
    private List<String> data;

    private RecyclerView recyclerView;
    private HorizontalRecyclerViewAdapter horizontalRecyclerViewAdapter;
    private TextView title;
    private String[] titleList = {"瀏覽全部分類", "瀏覽平光鏡", "瀏覽太陽鏡", "專題分享"};
    private LinearLayout fragmentTitle;

    private static final String TAG_POSITION = "fragmentPositionTag";

    int position;
    //接口 实现点击更换fragment


    private PPWToFragment ppwToFragment;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ppwToFragment = (PPWToFragment) context;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_all;
    }

    @Override
    protected void initView(View view) {
        //初始化接口

        recyclerView = bindView(R.id.all_fragment_rv);
        title = bindView(R.id.all_fragment_title);
        fragmentTitle = bindView(R.id.all_fragment_classify);

        Bundle arg = getArguments();
        position = arg.getInt(TAG_POSITION);

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


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        horizontalRecyclerViewAdapter = new HorizontalRecyclerViewAdapter(getContext());
        title.setText(titleList[position]);

        List<String> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(i + "巴拉拉能量~~~~");
        }

        fragmentTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToppp();
            }
        });


        horizontalRecyclerViewAdapter.setData(data);
        recyclerView.setAdapter(horizontalRecyclerViewAdapter);

        horizontalRecyclerViewAdapter.setMyOnItemClickListener(this);


        //   if (popupWindow.isShowing()) {

        ppwListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        popupWindow.setFocusable(true);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setTouchable(true);
                        Log.d("AllFragment", "position:" + position);
                        if (position == 5) {
                            position = 0;
                        }
                        if (position == 6) {
                            //退出事件
                            position = 0;
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("確定退出登錄");
                            builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //點擊確定退出登錄
                                    //移除授权
                                    Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                                    if (weibo.isValid())
                                        weibo.removeAccount(true);
                                    Platform qq = ShareSDK.getPlatform(QQ.NAME);
                                    if (qq.isValid()) {

                                        qq.removeAccount();
                                    }


                                }
                            }).setNegativeButton("取消", null).show();
                        }

                        ppwToFragment.getPosition(position);
                        ppwAdapter.setType(position);
                        popupWindow.dismiss();

                    }
                }
        );

        // }
    }


    /**
     * 静态工厂方法需要一个int型的值来初始化fragment的参数，
     * 然后返回新的fragment到调用者
     * <p>
     * newInstance()方法是一种“静态工厂方法",让我们在初始化和设置一个新的fragment的时候省去调用它的构造函数和额外的setter方法
     * 为你的Fragment提供静态工厂方法是一种好的做法,因为它封装和抽象了在客户端构造对象所需的步骤。
     */
    //Fragment的实例化
    //   复用Fragment复写该方法,并将Fragment进行编号
    public static AllFragment newInstance(int position) {
        Bundle args = new Bundle();
        //在这里只需 给bundle赋值 ( key_value的形式)
        args.putInt(TAG_POSITION, position);
        AllFragment fragment = new AllFragment();
        fragment.setArguments(args);
        return fragment;

        //使用静态工厂方法，将外部传入的参数可以通过Fragment.setArgument保存在它自己身上，
        // 这样我们可以在Fragment.onCreate(...)调用的时候将这些参数取出来。
    }

    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(getContext(), GoodsDetails.class);
        startActivity(intent);
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
