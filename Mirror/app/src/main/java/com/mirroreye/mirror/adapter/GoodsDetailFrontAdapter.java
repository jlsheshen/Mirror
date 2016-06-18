package com.mirroreye.mirror.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.base.view.NoTouchScrollView;

/**
 * Created by ${jl} on 16/6/17.
 */
public class GoodsDetailFrontAdapter extends BaseAdapter {
    Context context;


    public GoodsDetailFrontAdapter(Context context) {
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
//        if (convertView == null){
//        convertView = LayoutInflater.from(context).inflate(R.layout.item_goods_detail_front_lv,parent,false);
//        }
        return LayoutInflater.from(context).inflate(R.layout.item_goods_detail_front_lv,parent,false);
    }
}
