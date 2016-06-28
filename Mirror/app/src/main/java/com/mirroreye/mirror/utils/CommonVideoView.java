package com.mirroreye.mirror.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.ui.show.WearShowActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by liangduo on 16/6/24.
 */
public class CommonVideoView extends FrameLayout implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, View.OnTouchListener,
        View.OnClickListener, Animator.AnimatorListener, SeekBar.OnSeekBarChangeListener {

    private final int UPDATE_VIDEO_SEEKBAR = 1000;
    private Context context;
    private FrameLayout viewBox;
    private MyVideoView videoView;
    private LinearLayout videoPauseBtn;
    private LinearLayout screenSwitchBtn;
    private LinearLayout touchStatusView;
    private LinearLayout videoControllerLayout;
    private ImageView touchStatusImg;
    private ImageView videoPlayImg;
    private ImageView videoPauseImg;
    private TextView touchStatusTime;
    private TextView videoCurTimeText;
    private TextView videoTotalTimeText;
    private SeekBar videoSeekBar;

    private boolean touch = false;


    private ProgressBar progressBar;

    private int duration;
    private String formatTotalTime;

    private Timer timer = new Timer();

    private float touchLastX;
    //定义用seekBar当前的位置，触摸快进的时候显示时间
    private int position;
    private int touchStep = 1000;//快进的时间，1秒
    private int touchPosition = -1;

    private boolean videoControllerShow = true;//底部状态栏的显示状态
    private boolean animation = false;


    private Handler videoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_VIDEO_SEEKBAR:
                    if (videoView.isPlaying()) {
                        videoSeekBar.setProgress(videoView.getCurrentPosition());
                    }
                    break;
            }
        }
    };

    public CommonVideoView(Context context) {
        this(context, null);
    }

    public CommonVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void start(String url) {
        /////////////////////////
        viewBox.setVisibility(VISIBLE);

        videoPauseBtn.setEnabled(false);
        videoSeekBar.setEnabled(false);
        videoView.setVideoURI(Uri.parse(url));
        videoView.start();
    }

    public void stopPlayback() {
        videoView.stopPlayback();
    }

    public void setFullScreen() {
        touch = true;
        WearShowActivity.getTitleLayout().setVisibility(GONE);
        touchStatusImg.setImageResource(R.mipmap.iconfont_exit);
        Log.d("CommonVideoView", "全屏");
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth()-40;
        int height = wm.getDefaultDisplay().getHeight()-40;
        WearShowActivity.getHead().setLayoutParams(new ListView.LayoutParams(width,height));
        WearShowActivity.getWearShowBtnLayout().setVisibility(GONE);
        WearShowActivity.getListView().setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        videoView.requestLayout();
    }


    public void setNormalScreen() {
        touch = false;
        WearShowActivity.getTitleLayout().setVisibility(VISIBLE);
        Log.d("CommonVideoView", "正常平");
        touchStatusImg.setImageResource(R.mipmap.iconfont_enter_32);
        WearShowActivity.getHead().setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        WearShowActivity.getWearShowBtnLayout().setVisibility(VISIBLE);
        WearShowActivity.getListView().setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        videoView.requestLayout();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return touch;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.common_video_view, null);
        viewBox = (FrameLayout) view.findViewById(R.id.viewBox);
        videoView = (MyVideoView) view.findViewById(R.id.videoView);
        videoPauseBtn = (LinearLayout) view.findViewById(R.id.videoPauseBtn);
        screenSwitchBtn = (LinearLayout) view.findViewById(R.id.screen_status_btn);
        videoControllerLayout = (LinearLayout) view.findViewById(R.id.videoControllerLayout);
        touchStatusView = (LinearLayout) view.findViewById(R.id.touch_view);
        touchStatusImg = (ImageView) view.findViewById(R.id.touchStatusImg);
        touchStatusTime = (TextView) view.findViewById(R.id.touch_time);
        videoCurTimeText = (TextView) view.findViewById(R.id.videoCurTime);
        videoTotalTimeText = (TextView) view.findViewById(R.id.videoTotalTime);
        videoSeekBar = (SeekBar) view.findViewById(R.id.videoSeekBar);
//        videoPlayImg = (ImageView) view.findViewById(R.id.videoPlayImg);
//        videoPlayImg.setVisibility(GONE);
        videoPauseImg = (ImageView) view.findViewById(R.id.videoPauseImg);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
//        videoCloseIv = (ImageView) view.findViewById(R.id.wear_show_stop_play_iv);


        videoPauseBtn.setOnClickListener(this);
        videoSeekBar.setOnSeekBarChangeListener(this);
//        videoPauseBtn.setOnClickListener(this);
        videoView.setOnPreparedListener(this);
        //注册一个回调函数，视频播放完成后调用
        videoView.setOnCompletionListener(this);
        screenSwitchBtn.setOnClickListener(this);
//        videoPlayImg.setOnClickListener(this);
        //注册在设置或播放过程中发生错误时调用的回调函数。如果未指定回调函数，或回调函数返回false，VideoView 会通知用户发生了错误。
        videoView.setOnErrorListener(this);
        viewBox.setOnTouchListener(this);
        viewBox.setOnClickListener(this);
//        videoCloseIv.setOnClickListener(this);
        addView(view);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        duration = videoView.getDuration();//获得所播放视频的总时间
        int[] time = getMinuteAndSecond(duration);
        videoTotalTimeText.setText(String.format("%02d:%02d", time[0], time[1]));//设置播放时间总长tv
        formatTotalTime = String.format("%02d:%02d", time[0], time[1]);//总时间
        videoSeekBar.setMax(duration);//设置seekBar的总长度微视频播放的总时间
        progressBar.setVisibility(View.GONE);

        mp.start();
        videoPauseBtn.setEnabled(true);
        videoSeekBar.setEnabled(true);
//        videoCloseIv.setEnabled(true);
        videoPauseImg.setImageResource(R.mipmap.icon_video_pause);
//        timer.schedule(timerTask, 0, 1000);
        reScheduleTimer(1);
    }

//
//    private int[] getMinute AndSecond(int mils) {
//        mils /= 1000;
//        int[] time = new int[2];
//        time[0] = mils / 60;
//        time[1] = mils % 60;
//        return time;
//    }

    public void reScheduleTimer(int duration) {
//        timer.cancel();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                videoHandler.sendEmptyMessage(UPDATE_VIDEO_SEEKBAR);
            }
        }, 0, duration * 1000);//一可以使用同一Timer对象，但不能执行同一个TimerTask两次
    }


    /**
     * VideoView播放结束执行的方法
     * @param mp
     */
    @Override
    public void onCompletion(MediaPlayer mp) {
        videoView.seekTo(0);
        videoSeekBar.setProgress(0);
        videoPauseImg.setImageResource(R.mipmap.icon_video_play);
//        videoPlayImg.setVisibility(View.VISIBLE);
        /////////////////////////////
        int j = getResources().getConfiguration().orientation;
        if (j == Configuration.ORIENTATION_PORTRAIT) {
            videoView.stopPlayback();
        } else if (j == Configuration.ORIENTATION_LANDSCAPE) {
            ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            videoView.stopPlayback();
        }
        viewBox.setVisibility(GONE);
        WearShowActivity.getLvHeadVvStartIv().setVisibility(VISIBLE);

//        WearShowActivity.class.
        videoView.stopPlayback();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!videoView.isPlaying()) {
                    return false;
                }
                float downX = event.getRawX();
                touchLastX = downX;
                Log.d("FilmDetailActivity", "downX" + downX);
                this.position = videoView.getCurrentPosition();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!videoView.isPlaying()) {
                    return false;
                }
                float currentX = event.getRawX();
                float deltaX = currentX - touchLastX;
                float deltaXAbs = Math.abs(deltaX);
                if (deltaXAbs > 1) {
                    if (touchStatusView.getVisibility() != View.VISIBLE) {
                        touchStatusView.setVisibility(View.VISIBLE);
                    }
                    touchLastX = currentX;
                    Log.d("FilmDetailActivity", "deltaX" + deltaX);
                    if (deltaX > 1) {
                        position += touchStep;
                        if (position > duration) {
                            position = duration;
                        }
                        touchPosition = position;
                        touchStatusImg.setImageResource(R.mipmap.ic_fast_forward_white_24dp);
                        int[] time = getMinuteAndSecond(position);
                        touchStatusTime.setText(String.format("%02d:%02d/%s", time[0], time[1], formatTotalTime));
                    } else if (deltaX < -1) {
                        position -= touchStep;
                        if (position < 0) {
                            position = 0;
                        }
                        touchPosition = position;
                        touchStatusImg.setImageResource(R.mipmap.ic_fast_rewind_white_24dp);
                        int[] time = getMinuteAndSecond(position);
                        touchStatusTime.setText(String.format("%02d:%02d/%s", time[0], time[1], formatTotalTime));
                        //mVideoView.seekTo(position);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (touchPosition != -1) {
                    videoView.seekTo(touchPosition);
                    touchStatusView.setVisibility(View.GONE);
                    touchPosition = -1;
                    if (videoControllerShow) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    private int[] getMinuteAndSecond(int mils) {
        mils /= 1000;
        int[] time = new int[2];
        time[0] = mils / 60;
        time[1] = mils % 60;
        return time;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.videoPlayImg:
//                videoView.start();
//                videoPlayImg.setVisibility(View.INVISIBLE);
//                videoPauseImg.setImageResource(R.mipmap.icon_video_pause);
//                break;
            case R.id.videoPauseBtn:
                if (videoView.isPlaying()) {
                    videoView.pause();
                    videoPauseImg.setImageResource(R.mipmap.icon_video_play);
//                    videoPlayImg.setVisibility(View.VISIBLE);
                } else {
                    videoView.start();
                    videoPauseImg.setImageResource(R.mipmap.icon_video_pause);
//                    videoPlayImg.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.viewBox:
                float curY = videoControllerLayout.getY();
                if (!animation && videoControllerShow) {
                    animation = true;
                    ObjectAnimator animator = ObjectAnimator.ofFloat(videoControllerLayout, "y",
                            curY, curY + videoControllerLayout.getHeight());
                    animator.setDuration(200);
                    animator.start();
                    animator.addListener(this);
                } else if (!animation) {
                    animation = true;
                    ObjectAnimator animator = ObjectAnimator.ofFloat(videoControllerLayout, "y",
                            curY, curY - videoControllerLayout.getHeight());
                    animator.setDuration(200);
                    animator.start();
                    animator.addListener(this);
                }
                break;
            case R.id.screen_status_btn:
                Log.d("CommonVideoView", "走了吧这里");
                int i = getResources().getConfiguration().orientation;
                if (i == Configuration.ORIENTATION_PORTRAIT) {
                    ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
                } else if (i == Configuration.ORIENTATION_LANDSCAPE) {
                    ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
                }
                break;

        }
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        this.animation = false;
        this.videoControllerShow = !this.videoControllerShow;
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int[] time = getMinuteAndSecond(progress);
        videoCurTimeText.setText(String.format("%02d:%02d", time[0], time[1]));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        videoView.pause();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        videoView.seekTo(videoSeekBar.getProgress());
        videoView.start();
//        videoPlayImg.setVisibility(View.INVISIBLE);
        videoPauseImg.setImageResource(R.mipmap.icon_video_pause);
    }
}
