package com.mirroreye.mirror.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mirroreye.mirror.R;

/**
 * Created by liangduo on 16/6/18.
 */
public class WearShowlvAdapter extends BaseAdapter {
    private int[] image;
    private Context context;

    public WearShowlvAdapter(Context context) {
        this.context = context;
    }

    public void setImage(int[] image) {
        this.image = image;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return image == null ?0 :image.length;
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
        ViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_wear_show,null);
            holder = new ViewHolder(convertView);
        }
        return null;
    }

    class ViewHolder {
        ImageView imageView;

        public ViewHolder(View itemView){
            imageView = itemView.findViewById(R.id.wear)
        }
    }
}
