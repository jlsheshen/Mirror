package com.mirroreye.mirror.ui.buy_detail;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.base.BaseActivity;
import com.mirroreye.mirror.base.value.InputParameter;
import com.mirroreye.mirror.bean.BuyDtailBean;
import com.mirroreye.mirror.ui.add_address.EditAddressActivity;
import com.mirroreye.mirror.ui.address_list.AddressListActivity;
import com.mirroreye.mirror.utils.Mydialog;
import com.mirroreye.mirror.utils.OkHttpClientManager;
import com.mirroreye.mirror.utils.SPUtils;
import com.squareup.okhttp.Request;

/**
 * Created by ${jl} on 16/6/18.
 */
public class BuyDetailActivity extends BaseActivity implements View.OnClickListener {
    TextView consigneeTv,addressTv,phoneTv,commodityNmaeTv,commodityDescribeTv,changeAddressTv,ensureBuyTv;
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
        ensureBuyTv = bindView(R.id.buy_detail_commodity_surebuy_tv);

    }

    @Override
    protected void initData() {
        token = (String) SPUtils.get(this,InputParameter.TOKEN,"失败了");

        getAddressDate();
        //changeAddressTv

        ensureBuyTv.setOnClickListener(this);
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
                            Intent intent = new Intent(BuyDetailActivity.this, AddressListActivity.class);
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
                            Intent intent = new Intent(BuyDetailActivity.this, EditAddressActivity.class);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buy_detail_commodity_surebuy_tv:
                showPayDialog();
                break;
        }
    }

    private void showPayDialog() {
        final Mydialog mydialog = new Mydialog(this);
        mydialog.setTitle("选择支付方式");
        mydialog.setButton1Event("微信支付", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BuyDetailActivity.this, "我也点击啦", Toast.LENGTH_SHORT).show();
                mydialog.dismiss();
            }
        });
        mydialog.setButton2Event("支付宝支付", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BuyDetailActivity.this, "点击啦啦啦啦啦", Toast.LENGTH_SHORT).show();
                mydialog.dismiss();
            }
        });
        mydialog.show();

    }

}
