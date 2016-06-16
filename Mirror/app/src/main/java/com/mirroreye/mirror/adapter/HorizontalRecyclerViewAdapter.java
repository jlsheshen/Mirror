package com.mirroreye.mirror.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirroreye.mirror.R;

import java.util.List;

/**
 * Created by liangduo on 16/6/14.
 */
public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewAdapter.MyViewHolder> {
    private List<String> data;
    private Context context;

    //接口对象
    public MyOnItemClickListener myOnItemClickListener;
    //设置接口对象的set方法
    public void setMyOnItemClickListener(MyOnItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;
    }

    public HorizontalRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    //创建ViewHolder的方法
    //初始化行布局,传数值,需要布局的话就需要context
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(context).inflate(R.layout.item_horizontal_rv,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(item);
        return myViewHolder;
    }

    //RecyclerView里每一个itemView里的组件显示什么数据,都在此方法中设置
    //为行布局设置组件
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.model.setText(data.get(position));
        //如果接口对象不为空,则开始对itemView设置监听
        if (myOnItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //当itemView被点击时候,就会执行里面的代码
                    //getLayoutPosition是获得当前是第几条数据
                    int pos = holder.getLayoutPosition();
                    myOnItemClickListener.onItemClick(pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0:data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView price;
        ImageView showPicture;
        TextView goodsName;
        TextView productArea;
        TextView model;
         public MyViewHolder(View itemView) {
             super(itemView);
             price = (TextView) itemView.findViewById(R.id.item_horizontal_price);
             showPicture = (ImageView) itemView.findViewById(R.id.item_horizontal_iv);
             goodsName  = (TextView) itemView.findViewById(R.id.item_horizontal_goods_name);
             productArea = (TextView) itemView.findViewById(R.id.item_horizontal_product_area);
             model = (TextView) itemView.findViewById(R.id.item_horizontal_model);
         }
     }

    /**
     * ItemClick的回调接口
     */
    public interface MyOnItemClickListener{
        void onItemClick(int pos);
    }

}
