package com.mirroreye.mirror.ui.add_address;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.base.BaseActivity;
import com.mirroreye.mirror.bean.AddAddressBean;
import com.mirroreye.mirror.utils.OkHttpClientManager;
import com.mirroreye.mirror.utils.SPUtils;
import com.squareup.okhttp.Request;

/**
 * Created by liangduo on 16/6/20.
 * 添加新地址界面
 */
public class AddAddressActivity extends BaseActivity implements View.OnClickListener {
    private EditText addName;
    private EditText addPhone;
    private EditText addAddress;
    private Button submitAdd;
    private ImageView close;
    private TextView title;
    String token;

    @Override
    public int setLayout() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initView() {
        addName = bindView(R.id.add_address_name_et);
        addAddress = bindView(R.id.add_address_et);
        addPhone = bindView(R.id.add_address_phone_et);
        submitAdd = bindView(R.id.add_address_btn);
        close  =bindView(R.id.buy_detail_cancel_iv);
        title = bindView(R.id.personage_detail_title_tv);

    }

    @Override
    protected void initData() {
        title.setText("添加地址");

        //这是写的临时的
        SPUtils.put(this,"token","ffe6f5d40a8b0c366aa5214caba2c4b1");
        token = (String) SPUtils.get(this,"token","");

        submitAdd.setOnClickListener(this);
        close.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_address_btn:
                getSubmitAddress();
                break;
            case R.id.buy_detail_cancel_iv:
                finish();
                break;

        }
    }

    private void getSubmitAddress() {
        OkHttpClientManager.postAsyn("http://api101.test.mirroreye.cn/index.php/user/add_address",
                new OkHttpClientManager.ResultCallback<AddAddressBean>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.d("AddAddressActivity", e.toString());
                    }

                    @Override
                    public void onResponse(AddAddressBean response) {
                        Log.d("AddAddressActivity", response.getResult());

                    }
                },new OkHttpClientManager.Param[]{
                        new OkHttpClientManager.Param("token",token),
//                        new OkHttpClientManager.Param("add id",""+1225),
                        new OkHttpClientManager.Param("username",addName.getText().toString()),
                        new OkHttpClientManager.Param("cellphone",addPhone.getText().toString()),
                        new OkHttpClientManager.Param("addr_info",addAddress.getText().toString())
                });
    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }
}
