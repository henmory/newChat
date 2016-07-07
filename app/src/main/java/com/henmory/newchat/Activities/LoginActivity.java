package com.henmory.newchat.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.henmory.newchat.Common.CommonActivity;
import com.henmory.newchat.Common.CommonActivityManager;
import com.henmory.newchat.DataParse.ParseJsonData;
import com.henmory.newchat.Interface.FailCallback;
import com.henmory.newchat.Interface.SuccessCallback;
import com.henmory.newchat.Main.Config;
import com.henmory.newchat.Login.Login;
import com.henmory.newchat.Message.LoadMessage;
import com.henmory.newchat.Message.Message;
import com.henmory.newchat.R;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends CommonActivity{

    private Button btn_login;
    private EditText editText_phone_num;
    private EditText editText_captcha;

    private String phone_num;
    private String token;
    private String captcha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        bindViews();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                captcha = editText_captcha.getText().toString();
                System.out.println("login phone num = " + phone_num);
                System.out.println("captcha = " + captcha);

                final ProgressDialog pd =  new ProgressDialog(LoginActivity.this, ProgressDialog.STYLE_SPINNER);
                pd.setCancelable(false);
                pd.setMessage("登录中，请稍后...");
                pd.show();

                new Login(LoginActivity.this, new SuccessCallback() {
                    @Override
                    public void onSuccess(Object string) {
                        pd.dismiss();
                        token = (String) string;
                        Config.cacheToken(LoginActivity.this, (String)string);
                        Intent intent = new Intent(LoginActivity.this, MessageListMainActivity.class);
                        intent.putExtra(Config.KEY_PHONE_NUM, phone_num);
                        intent.putExtra(Config.KEY_TOKEN, token);
                        startActivity(intent);
                        finish();
                        CommonActivityManager.finishActivityByClassName(GetCaptchaActivity.class);
                        System.out.println("login successfully");
                    }
                }, new FailCallback() {
                    @Override
                    public void onFail(int errorCode) {
                        pd.dismiss();
                        switch(errorCode){
                            case Config.NET_CONNECTION_ERROR:
                                Snackbar.make(findViewById(R.id.CoordinatorLayout), "errorCode = " + errorCode, Snackbar.LENGTH_SHORT).show();
                                break;
                            case Config.NET_CONNECTION_ESTABLISH_FAIL:
                                Snackbar.make(findViewById(R.id.CoordinatorLayout), "net establish" , Snackbar.LENGTH_SHORT).show();
                                break;
                            default:
                                System.out.println("login failed, and errorCode = " + errorCode);
                                break;
                        }
                    }
                },phone_num, captcha);
            }
        });

    }

    private void bindViews(){
        editText_phone_num = (EditText) findViewById(R.id.login_tv_phone_num_value);
        editText_phone_num.setText(Config.getCachePhoneNum(this));
        btn_login = (Button) findViewById(R.id.login_btn_login);
        editText_captcha = (EditText) findViewById(R.id.login_edit_captcha);
        phone_num = editText_phone_num.getText().toString();
        editText_captcha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (editText_captcha.getTextSize() == 0){
                    btn_login.setEnabled(false);
                }else{
                    btn_login.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
