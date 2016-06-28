package com.mirroreye.mirror.ui.goods;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.adapter.GoodsDetailFrontAdapter;
import com.mirroreye.mirror.adapter.GoodsDetailLowerAdapter;
import com.mirroreye.mirror.base.BaseActivity;
import com.mirroreye.mirror.base.view.MaxHighNoTouchListView;
import com.mirroreye.mirror.base.view.NoTouchScrollView;
import com.mirroreye.mirror.base.view.ScrollListenerListView;
import com.mirroreye.mirror.listener.OnFrontScrollListener;
import com.mirroreye.mirror.listener.OnListScrollListener;
import com.mirroreye.mirror.listener.OnSetBlowBarScrollListener;
import com.mirroreye.mirror.ui.buy_detail.BuyDetailActivity;
import com.mirroreye.mirror.ui.show.WearShowActivity;

/**
 * Created by liangduo on 16/6/15.
 */
public class GoodsDetailsActivity extends BaseActivity implements OnFrontScrollListener,OnListScrollListener,OnSetBlowBarScrollListener {
    private ScrollListenerListView scrollListenerListView;
    private NoTouchScrollView noTouchScrollView;
    private MaxHighNoTouchListView maxHighNoTouchListView;
    private LinearLayout blowBar;
    private ObjectAnimator intoAnim;
    private ObjectAnimator outAnim;
    private CountDownTimer countDownTimer;
    private ImageView backIv,goShowIv,goBuyIv;

    @Override
    public int setLayout() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void initView() {
        scrollListenerListView = bindView(R.id.goods_detail_lower_lv);
        noTouchScrollView = bindView(R.id.goods_detail_sv);
        maxHighNoTouchListView = bindView(R.id.goods_detail_front_lv);
        blowBar = bindView(R.id.goods_detail_blow_bar);
        backIv = bindView(R.id.goods_detail_back_iv);
        goShowIv = bindView(R.id.goods_detail_goshow_iv);
        goBuyIv = bindView(R.id.goods_detail_gobuydetail_iv);

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        goShowIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodsDetailsActivity.this,WearShowActivity.class);
                startActivity(intent);

            }
        });
        goBuyIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodsDetailsActivity.this,BuyDetailActivity.class);
                startActivity(intent);

            }
        });


        scrollListenerListView.setOnListScrollListener(this);
        noTouchScrollView.setOnFrontScrollListener(this);
        maxHighNoTouchListView.setOnFrontScrollListener(this);



        GoodsDetailFrontAdapter frontAdapter = new GoodsDetailFrontAdapter(this);
        GoodsDetailLowerAdapter lowerAdapter = new GoodsDetailLowerAdapter(this);

        lowerAdapter.setOnSetBlowBarScrollListener(this);

        maxHighNoTouchListView.setAdapter(frontAdapter);
        scrollListenerListView.setAdapter(lowerAdapter);
        lowerAdapter.setScrollView(noTouchScrollView);

    }



    @Override
    protected void initData() {
        intoAnim = ObjectAnimator.ofFloat(blowBar,"translationX",-720,0);
        intoAnim.setInterpolator(new DecelerateInterpolator());
        intoAnim.setDuration(1000);

        outAnim = ObjectAnimator.ofFloat(blowBar,"translationX",0,-720);
        outAnim.setInterpolator(new DecelerateInterpolator());
        outAnim.setDuration(1000);

    }

    @Override
    public void letScroll() {
        scrollListenerListView.setScrollY(getScrollY(maxHighNoTouchListView));

    }

    @Override
    public void onListViewChange() {
        maxHighNoTouchListView.setScrollY( getScrollY(scrollListenerListView)/2);
    }

    /**
     * 这是主要控制滑动的方法,用来获取listView的滑动距离
     *
     * @param l
     * @return
     */
    public int getScrollY(ListView l) {
        View c = l.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = l.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight() ;
    }

    @Override
    public void blowBarInto() {
        blowBar.setVisibility(View.VISIBLE);
        intoAnim.start();

    }

    @Override
    public void blowBarOut() {
        outAnim.start();
//        blowBar.setVisibility(View.GONE);
    }



}
