package com.mirroreye.mirror.ui.show;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.adapter.WearShowLvAdapter;
import com.mirroreye.mirror.base.BaseActivity;
import com.mirroreye.mirror.utils.CommonVideoView;


/**
 * Created by liangduo on 16/6/16.
 * 佩戴图集
 */
public class WearShowActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static RelativeLayout wearShowBtnLayout;
    private RelativeLayout wearShowAty;
    public static ListView wearShowLv;
    private int[] images = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e};
    private WearShowLvAdapter wearShowLvAdapter;

    private ImageView lvHeadVvIv;
    public CommonVideoView lvHeadVv;
    public static ImageView lvHeadVvStartIv;
    private ImageView stopPlayIv;

    private PopupWindow wearImagePopupWindow;
    private ImageView back;
    private ImageView buy;
    private TextView title;

    public static View head;
    public static LinearLayout titleLayout;

    private String videoUrl = "http://7xr7f7.com2.z0.glb.qiniucdn.com/Jimmy%20fairly%20-%20Spring%202014-HD.mp4";

    @Override
    public int setLayout() {
        return R.layout.activity_wear_show;
    }

    @Override
    protected void initView() {
        wearShowAty = bindView(R.id.activity_wearshow);
        wearShowLv = bindView(R.id.wear_show_lv);

        back = bindView(R.id.wear_show_back);
        buy = bindView(R.id.wear_show_buy);
        title = bindView(R.id.personage_detail_title_tv);
        titleLayout = bindView(R.id.wear_show_title);
        wearShowBtnLayout = bindView(R.id.wear_show_button_layout);
    }

    @Override
    protected void initData() {
        title.setText("商品细节展示");
        back.setOnClickListener(this);
        buy.setOnClickListener(this);

        wearShowLv.setOnItemClickListener(this);

        head = LayoutInflater.from(this).inflate(R.layout.lv_head_show, null);
        lvHeadVvIv = (ImageView) head.findViewById(R.id.wear_show_head_iv);
        lvHeadVv = (CommonVideoView) head.findViewById(R.id.common_videoView);
        lvHeadVvStartIv = (ImageView) head.findViewById(R.id.wear_head_vv_start_iv);
        stopPlayIv = (ImageView) head.findViewById(R.id.wear_show_stop_play_iv);
        wearShowLv.addHeaderView(head);

        lvHeadVvStartIv.setOnClickListener(this);
        stopPlayIv.setOnClickListener(this);

        wearShowLvAdapter = new WearShowLvAdapter(this);
        wearShowLvAdapter.setImage(images);
        wearShowLv.setAdapter(wearShowLvAdapter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wear_show_back:
                finish();
                break;
            case R.id.wear_show_buy:
//                Toast.makeText(this, "不要點我", Toast.LENGTH_SHORT).show();
                break;
            case R.id.wear_head_vv_start_iv:
                stopPlayIv.setVisibility(View.VISIBLE);
                lvHeadVvStartIv.setVisibility(View.GONE);
                lvHeadVv.setVisibility(View.VISIBLE);
                lvHeadVv.start(videoUrl);
                break;
            case R.id.wear_show_stop_play_iv:
                lvHeadVv.setVisibility(View.GONE);
                stopPlayIv.setVisibility(View.GONE);
                lvHeadVvStartIv.setVisibility(View.VISIBLE);
                int i = getResources().getConfiguration().orientation;
                if (i == Configuration.ORIENTATION_PORTRAIT) {
                    lvHeadVv.stopPlayback();
                } else if (i == Configuration.ORIENTATION_LANDSCAPE) {
                     this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
                    lvHeadVv.stopPlayback();
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, "position:" + position, Toast.LENGTH_SHORT).show();
        Log.d("WearShowActivity", "走没走");
        if (position > 0) {
            showPopupWindow(position);
            wearImagePopupWindow.isShowing();
            wearImagePopupWindow.showAtLocation(wearShowAty, Gravity.CENTER, 0, 0);
        }
    }

    private void showPopupWindow(int pos) {
        wearImagePopupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View v = LayoutInflater.from(this).inflate(R.layout.ppw_wear_show, null);
        ImageView popupImageView = (ImageView) v.findViewById(R.id.ppw_wear_show_iv);

        /**
         * 这是等以后有实际数据的时候用的
         */
//        int picture = (int) wearShowLv.getItemAtPosition(pos);

        wearImagePopupWindow.setContentView(v);
        popupImageView.setImageResource(images[pos - 1]);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wearImagePopupWindow.dismiss();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            lvHeadVv.setFullScreen();
        }else {
            lvHeadVv.setNormalScreen();
        }
    }

    public static ImageView getLvHeadVvStartIv(){
        return lvHeadVvStartIv;
    }

    public static  LinearLayout getTitleLayout(){
        return  titleLayout;
    }

    public static View getHead(){
        return head;
    }

    public static ListView getListView(){
        return wearShowLv;
    }

    public static RelativeLayout getWearShowBtnLayout(){
        return wearShowBtnLayout;
    }

}