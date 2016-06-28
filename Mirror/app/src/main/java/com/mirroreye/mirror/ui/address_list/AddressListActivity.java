package com.mirroreye.mirror.ui.address_list;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.adapter.AddressListAdapter;
import com.mirroreye.mirror.base.BaseActivity;
import com.mirroreye.mirror.base.value.InputParameter;
import com.mirroreye.mirror.base.view.SlidingItemView;
import com.mirroreye.mirror.bean.AddressListBean;
import com.mirroreye.mirror.bean.VerificationBean;
import com.mirroreye.mirror.listener.RvDeteleDataListener;
import com.mirroreye.mirror.listener.SetViewHeightListener;
import com.mirroreye.mirror.ui.add_address.AddAddressActivity;
import com.mirroreye.mirror.utils.OkHttpClientManager;
import com.mirroreye.mirror.utils.SPUtils;
import com.squareup.okhttp.Request;

/**
 * Created by ${jl} on 16/6/21.
 */
public class AddressListActivity extends BaseActivity implements RvDeteleDataListener{
    private RecyclerView recyclerView;
    private AddressListAdapter adapter;
    private String token;
    private TextView addTv,titleTv,addAddressTv;
    private ImageView blackIv;

    @Override
    public int setLayout() {
        return R.layout.activity_address_list;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.address_list_rv);
        addTv = bindView(R.id.address_list_add_tv);
        //titleTv = bindView(R.id.personage_detail_title_tv);
        //titleTv.setText("我的所有地址");
        addAddressTv = bindView(R.id.address_list_add_tv);
        addAddressTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressListActivity.this , AddAddressActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
         token = (String) SPUtils.get(this, InputParameter.TOKEN,"错了");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddressListAdapter(this);
        recyclerView.setAdapter(adapter);
        getData();
        adapter.setDeteleListener(this);



    }


    private void getData(){
        OkHttpClientManager.postAsyn("http://api101.test.mirroreye.cn/index.php/user/address_list",
                new OkHttpClientManager.ResultCallback<AddressListBean>() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(AddressListBean response) {

                adapter.setDataBeanList(response.getData().getList());


            }
        },new OkHttpClientManager.Param[]{
                new OkHttpClientManager.Param(InputParameter.TOKEN,token ),
                new OkHttpClientManager.Param(InputParameter.DEVICE_TYPE,"1"),
                new OkHttpClientManager.Param(InputParameter.PAGE,""),
                new OkHttpClientManager.Param(InputParameter.LAST_TIME,"")});

    }

    @Override
    public void deteleItem(int pos, String addId) {
        OkHttpClientManager.postAsyn("http://api101.test.mirroreye.cn/index.php/user/del_address",
                new OkHttpClientManager.ResultCallback<VerificationBean>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.d("AddressListActivity", "失败楼");

                    }

                    @Override
                    public void onResponse(VerificationBean response) {
                        Log.d("AddressListActivity", "成功喽");


                    }
                },new OkHttpClientManager.Param[]{
                        new OkHttpClientManager.Param(InputParameter.TOKEN,token),
                        new OkHttpClientManager.Param(InputParameter.ADDR_ID,addId)

                });




    }
}
