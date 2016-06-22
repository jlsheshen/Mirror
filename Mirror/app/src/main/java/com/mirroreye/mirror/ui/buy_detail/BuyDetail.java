package com.mirroreye.mirror.ui.buy_detail;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.adapter.AddressListAdapter;
import com.mirroreye.mirror.base.BaseActivity;
import com.mirroreye.mirror.base.value.InputParameter;
import com.mirroreye.mirror.bean.BuyDtailBean;
import com.mirroreye.mirror.ui.add_address.AddAddressActivity;
import com.mirroreye.mirror.ui.add_address.EditAddressActivity;
import com.mirroreye.mirror.ui.address_list.AddressListActivity;
import com.mirroreye.mirror.utils.OkHttpClientManager;
import com.mirroreye.mirror.utils.SPUtils;
import com.squareup.okhttp.Request;

/**
 * Created by ${jl} on 16/6/18.
 */
public class BuyDetail extends BaseActivity {
    TextView consigneeTv,addressTv,phoneTv,commodityNmaeTv,commodityDescribeTv,changeAddressTv;
    ImageView commodityIv;
    LinearLayout noDateLay,haveDateLay;
    String token;

    @Override
    public int setLayout() {
        return R.layout.activity_buy_detail;
    }

    @Override
    protected void initView() {
        consigneeTv = bindView(R.id.buy_detail_consignee);
        addressTv = bindView(R.id.buy_detail_address);
        commodityNmaeTv = bindView(R.id.buy_detail_commodity_name_tv);
        commodityDescribeTv = bindView(R.id.buy_detail_commodity_describe_tv);
        changeAddressTv = bindView(R.id.buy_detail_changeaddress_tv);
        commodityIv = bindView(R.id.buy_detail_commodity_iv);
        noDateLay = bindView(R.id.buy_detail_nodate_layout);
        haveDateLay = bindView(R.id.buy_detail_havedate_layout);
        phoneTv = bindView(R.id.buy_detail_phone_tv);



    }

    @Override
    protected void initData() {
        token = (String) SPUtils.get(this,InputParameter.TOKEN,"失败了");
        Log.d("BuyDetail", "token" + token);

        getAddressDate();
        //changeAddressTv





    }
    void getAddressDate(){

        OkHttpClientManager.postAsyn("http://api101.test.mirroreye.cn/index.php/user/address_list", new OkHttpClientManager.ResultCallback<BuyDtailBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BuyDtailBean response) {


                if (response.getData() != null) {
                    changeAddressTv.setText("修改地址");
                    changeAddressTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(BuyDetail.this, AddressListActivity.class);
                            startActivity(intent);
                        }
                    });
                    noDateLay.setVisibility(View.GONE);
                    haveDateLay.setVisibility(View.VISIBLE);


                    for (BuyDtailBean.DataBean.ListBean listBean : response.getData().getList()) {
                        if (listBean.getIf_moren().equals("" + 1)) {

                            consigneeTv.setText(listBean.getUsername());
                            addressTv.setText(listBean.getAddr_info());
                            phoneTv.setText(listBean.getCellphone());
                            return;

                        }
                    }
                }else {
                    changeAddressTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(BuyDetail.this, EditAddressActivity.class);
                            startActivity(intent);
                        }
                    });


                }



            }
        },new OkHttpClientManager.Param[]{
                new OkHttpClientManager.Param(InputParameter.TOKEN,token),
                new OkHttpClientManager.Param(InputParameter.DEVICE_TYPE,"1"),
                new OkHttpClientManager.Param(InputParameter.PAGE,""),
                new OkHttpClientManager.Param(InputParameter.LAST_TIME,"")});

    }


}
