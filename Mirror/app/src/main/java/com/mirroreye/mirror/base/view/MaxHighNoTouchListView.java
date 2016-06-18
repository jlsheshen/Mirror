package com.mirroreye.mirror.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import com.mirroreye.mirror.listener.OnFrontScrollListener;

/**
 * 无折叠的listView
 * Created by ${jl} on 16/6/16.
 */
public class MaxHighNoTouchListView extends ListView {
    OnFrontScrollListener onFrontScrollListener;

    public void setOnFrontScrollListener(OnFrontScrollListener onFrontScrollListener) {
        this.onFrontScrollListener = onFrontScrollListener;
    }

    public MaxHighNoTouchListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        onFrontScrollListener.letScroll();
        return false;
    }
}