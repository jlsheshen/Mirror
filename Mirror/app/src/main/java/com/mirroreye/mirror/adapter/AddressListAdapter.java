package com.mirroreye.mirror.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.base.view.SlidingItemView;
import com.mirroreye.mirror.bean.AddressListBean;
import com.mirroreye.mirror.listener.RvDeteleDataListener;

import java.util.List;

/**
 * Created by ${jl} on 16/6/21.
 */
public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressViewHolder> {
    List<AddressListBean.DataBean.ListBean> dataBeanList;
    Context context;
    RvDeteleDataListener deteleListener;
    int pos;
    int height;

    public AddressListAdapter(Context context) {
        this.context = context;
    }

    public void setDataBeanList(List<AddressListBean.DataBean.ListBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
        notifyDataSetChanged();
    }

    public void setDeteleListener(RvDeteleDataListener deteleListener) {
        this.deteleListener = deteleListener;
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_address_list_rv,parent,false);
        view.setVisibility(View.VISIBLE);
        AddressViewHolder holder = new AddressViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(final AddressViewHolder holder, int position) {
        holder.nameTv.setText(dataBeanList.get(position).getUsername());
        holder.addressTv.setText(dataBeanList.get(position).getAddr_info());
        holder.phoneTv.setText(dataBeanList.get(position).getCellphone().toString());
        holder.slidingItemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_MOVE:
                    holder.deleteTv.setHeight(holder.layoutView.getHeight());
                        break;

                }

                return false;
            }
        });


        holder.deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deteleListener.deteleItem(pos,dataBeanList.get( holder.getLayoutPosition()).getAddr_id());
                deteData( holder.getLayoutPosition());
            }
        });



    }

    private void deteData(int pos) {
        dataBeanList.remove(pos);
        notifyItemRemoved(pos);
    }


    @Override
    public int getItemCount() {
        return dataBeanList == null? 0:dataBeanList.size();
    }

    class AddressViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv,addressTv,phoneTv,deleteTv;
        LinearLayout layoutView;
        SlidingItemView slidingItemView;
        public AddressViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.buy_detail_consignee);
            addressTv = (TextView) itemView.findViewById(R.id.buy_detail_address);
            phoneTv = (TextView) itemView.findViewById(R.id.buy_detail_phone_tv);
            deleteTv = (TextView) itemView.findViewById(R.id.address_list_item_detele);
            slidingItemView = (SlidingItemView) itemView.findViewById(R.id.sv);
            layoutView = (LinearLayout) itemView.findViewById(R.id.address_list_left_lay);

        }
    }
}
