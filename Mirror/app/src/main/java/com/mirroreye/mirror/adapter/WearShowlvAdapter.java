
package com.mirroreye.mirror.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mirroreye.mirror.R;


/**
 * Created by liangduo on 16/6/16.
 */
public class WearShowLvAdapter extends BaseAdapter {
    private int[] image;
    private Context context;

    public void setImage(int[] image) {
        this.image = image;
        notifyDataSetChanged();
    }

    public WearShowLvAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return image == null? 0: image.length;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_wear_show_lv,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.showIv.setImageResource(image[position]);
        return convertView;
    }

    class ViewHolder {
        ImageView showIv;
        private ViewHolder (View itemView){
            showIv = (ImageView) itemView.findViewById(R.id.item_wear_show_iv);
        }
    }
}