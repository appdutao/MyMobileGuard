package com.dutao.mymobileguard.activities;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dutao.mymobileguard.R;
import com.dutao.mymobileguard.base.BaseActivity;
import com.dutao.mymobileguard.bean.BlackNumberInfo;

public class CallSmsSafe extends BaseActivity {

    private EditText et_black_number;
    private CheckBox cb_phone;
    private CheckBox cb_sms;
    private Button bt_ok;
    private Button bt_cancel;
    private View dialog_add_blcacknumber;
    private SharedPreferences sp;
    private BlackNumberInfo blackNumberInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
    }

    /**
     * 初始化界面
     */
    private void initUi() {
        setContentView(R.layout.activity_callsms_safe);
    }

    /**
     * 添加按钮-点击时事件
     * @param view 当前的Button
     */
    public void addBlackNumber(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        dialog_add_blcacknumber = View.inflate(this, R.layout.dialog_add_blacknumber, null);
        et_black_number = (EditText) dialog_add_blcacknumber.findViewById(R.id.et_black_number);
        cb_phone = (CheckBox) dialog_add_blcacknumber.findViewById(R.id.cb_phone);
        cb_sms = (CheckBox) dialog_add_blcacknumber.findViewById(R.id.cb_sms);

        bt_ok = (Button) dialog_add_blcacknumber.findViewById(R.id.bt_ok);
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = et_black_number.getText().toString().trim();
                String mode = "0";
                if (TextUtils.isEmpty(number)){
                    Toast.makeText(CallSmsSafe.this,"您输入的手机号为空，请重新输入",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if(cb_phone.isChecked()&&cb_sms.isChecked()){
                        mode = "1";
                    }else if(cb_phone.isChecked()){
                        mode = "2";
                    }else if(cb_sms.isChecked()){
                        mode = "3";
                    }else{
                        Toast.makeText(CallSmsSafe.this,"您至少选择一种拦截方式",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                blackNumberInfo = new BlackNumberInfo(number,mode);
            }
        });
        bt_cancel = (Button) dialog_add_blcacknumber.findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
