package com.mirroreye.mirror.ui.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.base.BaseActivity;

/**
 * Created by liangduo on 16/6/15.
 * 註冊界面
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText registerPhoneEt;
    private EditText registerAuthCodeEt;
    private EditText registerPswEt;
    private Button registerAuthCodeBtn;
    private Button registerRegisterBtn;
    private ImageView registerClose;

    @Override
    public int setLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        registerClose = bindView(R.id.register_close);
        registerPhoneEt = bindView(R.id.register_phone_num_et);
        registerAuthCodeEt = bindView(R.id.register_code_et);
        registerPswEt = bindView(R.id.register_set_psw);
        registerAuthCodeBtn = bindView(R.id.register_send_code);
        registerRegisterBtn = bindView(R.id.register_register);
    }

    @Override
    protected void initData() {
        registerClose.setOnClickListener(this);
        registerRegisterBtn.setOnClickListener(this);
        registerAuthCodeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_close:
                finish();
                break;
            case R.id.register_send_code:
                Toast.makeText(this, "驗證碼已發送", Toast.LENGTH_SHORT).show();
                break;
            case R.id.register_register:
                Toast.makeText(this, "創建成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
