package com.mirroreye.mirror.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mirroreye.mirror.R;
import com.mirroreye.mirror.base.BaseActivity;
import com.mirroreye.mirror.utils.ThreeLogin;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

/**
 * Created by liangduo on 16/6/15.
 * 登錄界面
 */
public class LoginActivty extends BaseActivity implements View.OnClickListener {
    private ImageView backIv;
    private EditText loginPhoneEt;
    private EditText loginPswEt;
    private Button loginBtn;
    private Button loginRegister;
    private ImageView weibo;
    private ImageView wechat;
    private boolean hasInput = false;

    private EditTextChange watch = new EditTextChange();
    private ThreeLogin threeLogin;

    @Override
    public int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        backIv = bindView(R.id.login_close);
        loginPhoneEt = bindView(R.id.login_phone_num_et);
        loginPswEt = bindView(R.id.login_psw_et);
        loginBtn = bindView(R.id.login_login);
        loginRegister = bindView(R.id.login_register);
        weibo = bindView(R.id.login_weibo);
        wechat = bindView(R.id.login_wechat);
    }

    @Override
    protected void initData() {

        backIv.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        loginRegister.setOnClickListener(this);
        loginPhoneEt.addTextChangedListener(watch);
        loginPswEt.addTextChangedListener(watch);
        weibo.setOnClickListener(this);
        wechat.setOnClickListener(this);
//三方登录的类
        threeLogin = new ThreeLogin(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_close:
                finish();
                break;
            case R.id.login_login:
                if (hasInput == false) {
                    Toast.makeText(this, "請輸入電話號和密碼", Toast.LENGTH_SHORT).show();
                } else if (hasInput == true) {
                    Toast.makeText(this, "已登錄", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            //三方登錄
            case R.id.login_weibo:
                threeLogin.weiBo();
                break;
            case R.id.login_wechat:
                threeLogin.qq();
                break;

        }
    }

    /**
     * 這是什麼鬼  我也不知到
     * 這是寫了一個內部類
     * 反正這是 EditText 監聽
     */
    class EditTextChange implements TextWatcher {

        /**
         * @param s     输入框中改变前的字符串信息
         * @param start 输入框中改变前的字符串的起始位置
         * @param count 输入框中改变前后的字符串改变数量 默认为0
         * @param after 输入框中改变后的字符串与起始位置的偏移量
         */
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        /**
         * @param s      输入框中改变后的字符串信息
         * @param start  输入框中改变后的字符串的起始位置
         * @param before 输入框中改变前的字符串的位置 默认为0
         * @param count  输入框中改变后的一共输入字符串的数量
         */
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (loginPhoneEt.getText().length() > 0 && loginPswEt.getText().length() > 0) {
                Log.d("LoginActivty", "走啦");
                loginBtn.setBackgroundColor(Color.parseColor("#d85958"));
                hasInput = true;
            } else {
                loginBtn.setBackgroundColor(Color.parseColor("#f1f1f1"));
                hasInput = false;
            }
        }

        /**
         * @param s 输入框中改变后的一共输入字符串的数量
         */
        @Override
        public void afterTextChanged(Editable s) {
        }
    }


}
