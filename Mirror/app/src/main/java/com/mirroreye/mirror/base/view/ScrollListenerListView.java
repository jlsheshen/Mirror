package com.mirroreye.mirror.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.mirroreye.mirror.listener.OnListScrollListener;

/**
 * 通过监听,来监测list的滚动情况
 * Created by ${jl} on 16/6/17.
 */
public class ScrollListenerListView  extends ListView {
    OnListScrollListener onListScrollListener;

    public void setOnListScrollListener(OnListScrollListener onScrollListener) {
        this.onListScrollListener = onScrollListener;
    }

    public ScrollListenerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        onListScrollListener.onListViewChange();

    }

}
