package com.mirroreye.mirror.ui.story_detail;

import android.widget.ImageView;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.adapter.StoryDetailAdapter;
import com.mirroreye.mirror.base.BaseActivity;
import com.mirroreye.mirror.base.view.VerticalViewPager;
import com.mirroreye.mirror.listener.OnViewPagerChangeListener;


/**
 * Created by ${jl} on 16/6/15.
 */
public class StoryDetailActivity extends BaseActivity implements OnViewPagerChangeListener {
    ImageView backgroundIv,cancelIv,shareIv;
    VerticalViewPager verticalViewPager;
    StoryDetailAdapter storyDetailAdapter;
    @Override
    public int setLayout() {
        return R.layout.activity_story_detail;
    }

    @Override
    protected void initView() {
        backgroundIv = bindView(R.id.story_detail_background_iv);
        cancelIv = bindView(R.id.story_detail_cancel_iv);
        shareIv = bindView(R.id.story_detail_share_iv);
        verticalViewPager = bindView(R.id.story_detail_content_vvp);
        storyDetailAdapter = new StoryDetailAdapter(this);

    }

    @Override
    protected void initData() {
        storyDetailAdapter.setOnViewPagerChangeListener(this);
        verticalViewPager.setAdapter(storyDetailAdapter);


    }

    @Override
    public void changedViewPager(int pos) {



    }
}
