package com.mirroreye.mirror.ui.show;

import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.adapter.WearShowlvAdapter;
import com.mirroreye.mirror.base.BaseActivity;


/**
 * Created by liangduo on 16/6/16.
 * 佩戴图集
 */
public class WearShowActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private RelativeLayout wearShowAty;
    private ListView wearShowLv;
    private int[] images = {R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e};
    private WearShowlvAdapter wearShowLvAdapter;

    private ImageView lvHeadVvIv;
    private VideoView lvHeadVv;
    private ImageView lvHeadVvStartIv;
    private ImageView stopPlayIv;

    private PopupWindow wearImagePopupWindow;
    private MediaController mediaController;
    private ImageView back;
    private ImageView buy;
    private MyMediaController myMediaController ;

    private String videoUrl = "http://7rfkz6.com1.z0.glb.clouddn.com/360p_0043_XMTU3MjE1MDEwOA.mp4";



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

    }

    @Override
    protected void initData() {
        back.setOnClickListener(this);
        buy.setOnClickListener(this);

        wearShowLv.setOnItemClickListener(this);

        View head = LayoutInflater.from(this).inflate(R.layout.lv_head_show, null);
        lvHeadVvIv = (ImageView) head.findViewById(R.id.wear_show_head_iv);
        lvHeadVv = (VideoView) head.findViewById(R.id.wear_show_head_vv);
        lvHeadVvStartIv = (ImageView) head.findViewById(R.id.wear_head_vv_start_iv);
        stopPlayIv = (ImageView) head.findViewById(R.id.wear_show_stop_play_iv);
        wearShowLv.addHeaderView(head);

        lvHeadVvStartIv.setOnClickListener(this);
        stopPlayIv.setOnClickListener(this);

        wearShowLvAdapter = new WearShowlvAdapter(this);
        wearShowLvAdapter.setImage(images);
        wearShowLv.setAdapter(wearShowLvAdapter);


        // 视频结束时的监听事件
        lvHeadVv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                lvHeadVvIv.setVisibility(View.VISIBLE);
                lvHeadVvStartIv.setVisibility(View.VISIBLE);
                lvHeadVv.setVisibility(View.GONE);
                stopPlayIv.setVisibility(View.GONE);
            }
        });
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
                Uri uri = Uri.parse(videoUrl);
                mediaController = new MediaController(this,false);
                //VideoView与MediaController进行关联
                lvHeadVv.setMediaController(mediaController);
                lvHeadVv.setVideoURI(uri);
                //开始播放视频
                lvHeadVv.start();
                break;
            case R.id.wear_show_stop_play_iv:
                lvHeadVv.setVisibility(View.GONE);
                stopPlayIv.setVisibility(View.GONE);
                lvHeadVvStartIv.setVisibility(View.VISIBLE);
//                lvHeadVv.setMediaController(myMediaController);
//                lvHeadVv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mp) {
//                        lvHeadVv.start();
//                    }
//                });
//                lvHeadVv.setFocusableInTouchMode(false);
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

}