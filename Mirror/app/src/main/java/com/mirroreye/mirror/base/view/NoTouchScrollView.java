package com.mirroreye.mirror.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.mirroreye.mirror.listener.OnFrontScrollListener;

/**
 * Created by ${jl} on 16/6/17.
 */
public class NoTouchScrollView extends ScrollView {
        OnFrontScrollListener onFrontScrollListener;

    public void setOnFrontScrollListener(OnFrontScrollListener onFrontScrollListener) {
        this.onFrontScrollListener = onFrontScrollListener;
    }

    public NoTouchScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
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