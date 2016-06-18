package com.mirroreye.mirror.ui.login;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.base.BaseActivity;
import com.mirroreye.mirror.bean.VerificationBean;
import com.mirroreye.mirror.utils.OkHttpClientManager;
import com.squareup.okhttp.Request;

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
    int countDown;

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
                if(registerPhoneEt.getText().toString().isEmpty()){
                    Toast.makeText(this, "清输入电话号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(this, "驗證碼已發送", Toast.LENGTH_SHORT).show();
                getVerificationCide();



                break;
            case R.id.register_register:
                Toast.makeText(this, "该账号已注册", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void getVerificationCide() {
        OkHttpClientManager.postAsyn("http://api101.test.mirroreye.cn/index.php/user/send_code",
                new OkHttpClientManager.ResultCallback<VerificationBean>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        Log.d("RegisterActivity", e.toString());

                    }

                    @Override
                    public void onResponse(VerificationBean response) {
                        countDown = 60;
                        registerAuthCodeBtn.setClickable(false);
                        CountDownTimer timer = new CountDownTimer(59000,1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                registerAuthCodeBtn.setText(countDown-- + "秒后重新发送");
                            }

                            @Override
                            public void onFinish() {
                                registerAuthCodeBtn.setClickable(true);
                                registerAuthCodeBtn.setText("请输入验证码");
                            }
                        }.start();
                    }
                },new OkHttpClientManager.Param[]{
                        new OkHttpClientManager.Param("phone number",registerPhoneEt.getText().toString())
                });

    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }
}
