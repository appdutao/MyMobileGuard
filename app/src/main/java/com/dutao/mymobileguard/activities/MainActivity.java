package com.dutao.mymobileguard.activities;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.dutao.mymobileguard.R;
import com.dutao.mymobileguard.base.BaseActivity;
import com.dutao.mymobileguard.util.Md5Utils;
import com.dutao.mymobileguard.util.UIUtil;

public class MainActivity extends BaseActivity implements OnClickListener {

    private Button bt_anti_theft;
    private Handler myHandler;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_anti_theft = (Button) findViewById(R.id.bt_anti_theft);
        bt_anti_theft.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_anti_theft:
                if (isSetPwd()) {//已经设置了密码
                    showPwdDialog();
                } else {//没有设置密码
                    setPwd();
                }
                break;

            default:
                break;
        }

    }

    int errorCount = 1;

    /**
     * 输入密码
     */
    private void showPwdDialog() {
        Builder builder = new Builder(this);
        setPwdView = View.inflate(this, R.layout.dialog_enter_pwd, null);
        et_enter_pwd = (EditText) setPwdView.findViewById(R.id.et_enter_pwd);
        setPwdOk = (Button) setPwdView.findViewById(R.id.bt_ok);
        setPwdCancel = (Button) setPwdView.findViewById(R.id.bt_cancel);

        sp = getSharedPreferences("config", MODE_PRIVATE);
        setPwdOk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = et_enter_pwd.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)) {
                    UIUtil.makeToast(MainActivity.this, "密码不能为空");
                    return;
                }
                String storePwd = sp.getString("antiTheftPwd", null);
                if (TextUtils.equals(Md5Utils.encode(pwd), storePwd)) {

                } else {
                    UIUtil.makeToast(MainActivity.this, "密码不正确，您还有" + (3 - errorCount) + "次机会");
//                    if(errorCount == 3){
//                        
//                    }
                    errorCount++;
                }
            }
        });
        setPwdCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        builder.setView(setPwdView);
        dialog = builder.show();
    }

    private View setPwdView;
    private Button setPwdOk;
    private Button setPwdCancel;
    private EditText et_enter_pwd;
    private EditText setPwd;
    private EditText setPwdConfirm;

    private AlertDialog dialog;

    /**
     * 设置【手机防盗】密码
     */
    private void setPwd() {
        setPwdView = View.inflate(this, R.layout.dialog_setup_pwd, null);
        setPwdOk = (Button) setPwdView.findViewById(R.id.bt_ok);
        setPwdCancel = (Button) setPwdView.findViewById(R.id.bt_cancel);
        setPwd = (EditText) setPwdView.findViewById(R.id.et_pwd);
        setPwdConfirm = (EditText) setPwdView.findViewById(R.id.et_pwd_confirm);

        Builder builder = new Builder(this);

        setPwdOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String pwd1 = setPwd.getText().toString().trim();
                String pwd2 = setPwdConfirm.getText().toString().trim();

                if (TextUtils.isEmpty(pwd1) || TextUtils.isEmpty(pwd2)) {
                    UIUtil.makeToast(MainActivity.this, "密码不能为空，请重新输入");
                    return;
                }
                if (!TextUtils.equals(pwd1, pwd2)) {
                    UIUtil.makeToast(MainActivity.this, "密码不一致，请重新输入");
                    return;
                }

                Editor editor = sp.edit();
                editor.putString("antiTheftPwd", Md5Utils.encode(pwd1));
                editor.putBoolean("isSetPwd", true);
                editor.commit();
                dialog.dismiss();
                UIUtil.makeToast(MainActivity.this, "密码设置成功");
            }
        });

        setPwdCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        builder.setView(setPwdView);
        dialog = builder.show();
    }

    /**
     * 判断用户是否已设置了密码
     *
     * @return
     */
    private boolean isSetPwd() {
        sp = getSharedPreferences("config", MODE_PRIVATE);
        return sp.getBoolean("isSetPwd", false);
    }

    private class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {

        }

    }

}
