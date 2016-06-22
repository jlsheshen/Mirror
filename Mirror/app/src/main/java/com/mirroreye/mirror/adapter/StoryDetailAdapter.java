package com.mirroreye.mirror.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mirroreye.mirror.R;
import com.mirroreye.mirror.listener.OnViewPagerChangeListener;





/**
 * Created by ${jl} on 16/6/15.
 */
public class StoryDetailAdapter extends PagerAdapter{

    OnViewPagerChangeListener onViewPagerChangeListener;


    Context context;

    public StoryDetailAdapter(Context context) {
        this.context = context;
    }

    public void setOnViewPagerChangeListener(OnViewPagerChangeListener onViewPagerChangeListener) {
        this.onViewPagerChangeListener = onViewPagerChangeListener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_story_detail_vp_typetwo, null);
        container.addView(view);

        onViewPagerChangeListener.changedViewPager(position);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}


