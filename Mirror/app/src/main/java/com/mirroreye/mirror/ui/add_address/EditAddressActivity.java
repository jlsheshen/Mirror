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
 */
public class EditAddressActivity extends BaseActivity implements View.OnClickListener {
    private EditText editName;
    private EditText editPhone;
    private EditText editAddress;
    private Button submitEdit;
    private ImageView close;
    private TextView title;
    String token;


    @Override
    public int setLayout() {
        return R.layout.activity_edit_address;
    }

    @Override
    protected void initView() {
        editName = bindView(R.id.edit_address_name_et);
        editAddress = bindView(R.id.edit_address_et);
        editPhone = bindView(R.id.edit_address_phone_et);
        submitEdit = bindView(R.id.edit_address_btn);
        close  =bindView(R.id.buy_detail_cancel_iv);
        //title = bindView(R.id.personage_detail_title_tv);
    }

    @Override
    protected void initData() {
        title.setText("編輯地址");
        submitEdit.setOnClickListener(this);
        close.setOnClickListener(this);

        //这是写的临时的
        SPUtils.put(this,"token","ffe6f5d40a8b0c366aa5214caba2c4b1");
        token = (String) SPUtils.get(this,"token","");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buy_detail_cancel_iv:
                finish();
                break;
            case R.id.edit_address_btn:
                EditAddress();
                break;
        }

    }

    private void EditAddress() {
        OkHttpClientManager.postAsyn("http://api101.test.mirroreye.cn/index.php/user/edit_address",
                new OkHttpClientManager.ResultCallback<AddAddressBean>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.d("EditAddressActivity", "e:" + e);
                    }

                    @Override
                    public void onResponse(AddAddressBean response) {
                        Log.d("EditAddressActivity", token);

                        Log.d("EditAddressActivity", "response:" + response.getResult());

                    }
                }, new OkHttpClientManager.Param[]{
                        new OkHttpClientManager.Param("token", token),
                        new OkHttpClientManager.Param("addr_id", "1255"),
                        new OkHttpClientManager.Param("username", editName.getText().toString()),
                        new OkHttpClientManager.Param("cellphone", editPhone.getText().toString()),
                        new OkHttpClientManager.Param("addr_info", editAddress.getText().toString()),

                });
    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }
}
