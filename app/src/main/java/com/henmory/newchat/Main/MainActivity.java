package com.henmory.newchat.Main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Toast;

import com.henmory.newchat.Activities.GetCaptchaActivity;
import com.henmory.newchat.Activities.MessageListMainActivity;
import com.henmory.newchat.CustomView.MyNotification;
import com.henmory.newchat.Interface.FailCallback;
import com.henmory.newchat.Interface.SuccessCallback;
import com.henmory.newchat.Login.Login;
import com.henmory.newchat.Message.LoadMessage;
import com.henmory.newchat.Message.Message;
import com.henmory.newchat.Message.MessageAdapter;
import com.henmory.newchat.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String phone_num = Config.getCachePhoneNum(this);
        String token = Config.getToken(this);
//        token = null;
        if (TextUtils.isEmpty(phone_num) || TextUtils.isEmpty(token)) {
            System.out.println("cache datas is null, and login");
            startActivity(new Intent(this, GetCaptchaActivity.class));
        } else {
            System.out.println("have cache datas, and enter main acitvity");
            startActivity(new Intent(this, MessageListMainActivity.class));
//        MyNotification.showNotification1(this);
        }
        finish();

    }
}
