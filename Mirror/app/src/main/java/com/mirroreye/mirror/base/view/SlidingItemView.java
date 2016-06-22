package com.mirroreye.mirror.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.listener.SetViewHeightListener;

/**
 * Created by ${jl} on 16/6/21.
 */
public class SlidingItemView extends HorizontalScrollView {
    private int mScrollWidth;//滚动条能滚动的范围



    public SlidingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:
                changeScroll();

                return true;

//            case MotionEvent.ACTION_POINTER_UP:
//                callOnClick();
//                break;

        }

        return super.onTouchEvent(ev);
    }

    private void changeScroll() {
        if (getScrollX()>mScrollWidth/2){
            scrollTo(mScrollWidth,0);
        }else {
            scrollTo(0,0);
        }


    }
    //该方法用于放置子View的位置时调用
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //拿到MenuTextView的宽
        TextView menuTv = (TextView) findViewById(R.id.address_list_item_detele);
        mScrollWidth = menuTv.getWidth();

    }

}
