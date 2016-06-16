package com.mirroreye.mirror.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.base.value.V;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by 秦谦谦 on 16/6/16 16:17.
 */
public class PPWAdapter extends BaseAdapter {
    private List<String> data;
    private Context context;
    private int type;

    public void setType(int type) {
        this.type = type;
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }



    public PPWAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
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
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ppw, null);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        holder.textView.setText(data.get(position));
        holder.imageView.setVisibility(View.INVISIBLE);
        if (position==type){
                    holder.textView.setTextColor(Color.WHITE);
                    holder.imageView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    class MyViewHolder {
        TextView textView;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            textView = (TextView) itemView.findViewById(R.id.ppw_tv);
            imageView = (ImageView) itemView.findViewById(R.id.ppw_img);
        }
    }


}
