package com.mirroreye.mirror.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mirroreye.mirror.R;

/**
 * Created by liangduo on 16/6/22.
 * 自定义封装dialog
 */
public class Mydialog  {
    private Context context;
    private Dialog dialog;
    private LinearLayout wechatPayLayout;
    private LinearLayout alipayPayLayout;
    private TextView title;
    private TextView positiveTv,negativeTv;

    public Mydialog(Context context) {
        this.context = context;
        dialog = new Dialog(context, R.style.DialogTheme);
        dialog.setContentView(R.layout.dialog);
        title = (TextView) dialog.findViewById(R.id.dialog_title);
        wechatPayLayout = (LinearLayout) dialog.findViewById(R.id.dialog_positive_layout);
        alipayPayLayout = (LinearLayout) dialog.findViewById(R.id.dialog_nagtive_layout);
        positiveTv = (TextView) dialog.findViewById(R.id.dialog_positive_tv);
        negativeTv = (TextView) dialog.findViewById(R.id.dialog_nagtive_tv);
    }

    public void setButton1Event(final String content, View.OnClickListener listener){
        positiveTv.setText(content);
        wechatPayLayout.setOnClickListener(listener);
    }

    public void setButton2Event(String  content,View.OnClickListener listener){
        negativeTv.setText(content);
        alipayPayLayout.setOnClickListener(listener);
    }


    /**
     * @category Set The Content of the TextView
     */
    public void setTitle(String content) {
        title.setText(content);
    }

    public void show() {
        dialog.show();
    }

    public void hide() {
        dialog.hide();
    }

    public void dismiss() {
        dialog.dismiss();
    }

}
