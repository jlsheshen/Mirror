package com.mirroreye.mirror.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.base.view.NoTouchScrollView;
import com.mirroreye.mirror.listener.OnSetBlowBarScrollListener;
import com.mirroreye.mirror.utils.Share;

/**
 * Created by ${jl} on 16/6/17.
 */
public class GoodsDetailLowerAdapter extends BaseAdapter {
    Context context;
    NoTouchScrollView scrollView;
    OnSetBlowBarScrollListener onSetBlowBarScrollListener;
    ImageView shareIv;

    private boolean state = false;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setOnSetBlowBarScrollListener(OnSetBlowBarScrollListener onSetBlowBarScrollListener) {
        this.onSetBlowBarScrollListener = onSetBlowBarScrollListener;
    }

    public void setScrollView(NoTouchScrollView scrollView) {
        this.scrollView = scrollView;
    }

    public GoodsDetailLowerAdapter(Context context) {
        this.context = context;
    }
    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        if (position == 0 ){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_goods_detail_lower_frist_head_lv,parent,false);

            shareIv = (ImageView) convertView.findViewById(R.id.goods_share);
            shareIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Share share=new Share(context);
                    share.setUrl("http://www.baidu.com");
                    share.setText("我是杀上古");
                    share.setImageUrl("http://img3.duitang.com/uploads/item/201605/25/20160525093455_Qa2yR.jpeg");
                    share.showShare();
                }
            });


            if (state){
                onSetBlowBarScrollListener.blowBarOut();
                state = false;

            }
        }else if (position == 1){



            if (state){
                onSetBlowBarScrollListener.blowBarOut();
                state = false;

            }


            convertView = LayoutInflater.from(context).inflate(R.layout.item_goods_detail_lower_frist_head_lv,parent,false);
            convertView.setAlpha(0);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.goods_ceshi);
            imageView.setVisibility(View.INVISIBLE);


        }else {
            if (!state){
                onSetBlowBarScrollListener.blowBarInto();
                state = true;
            }
            convertView = LayoutInflater.from(context).inflate(R.layout.item_goods_detail_lower_lv,parent,false);
        }



        return convertView;
    }
    class FristViewHolder{
        ImageView imageView;

    }



}
