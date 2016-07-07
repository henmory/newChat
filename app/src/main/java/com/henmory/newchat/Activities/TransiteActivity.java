package com.henmory.newchat.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.henmory.newchat.Interface.FailCallback;
import com.henmory.newchat.Interface.SuccessCallback;
import com.henmory.newchat.Main.Config;
import com.henmory.newchat.Message.LoadMessage;
import com.henmory.newchat.Message.Message;
import com.henmory.newchat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 6/28/16.
 */
public class TransiteActivity extends AppCompatActivity {

    private List<Message> messageList;
    private String phone_num;
    private String token;
    private ProgressDialog pd;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list_main_is_null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        pd =  new ProgressDialog(TransiteActivity.this, ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        pd.setMessage("加载中，请稍后...");

        initMessageContent();
    }

    private void initMessageContent(){
        phone_num = getIntent().getStringExtra(Config.KEY_PHONE_NUM);
        token = getIntent().getStringExtra(Config.KEY_TOKEN);
        loadMessage();

    }

    void loadMessage(){
        new LoadMessage(TransiteActivity.this, new SuccessCallback() {
            @Override
            public void onSuccess(Object data) {
                messageList = (ArrayList<Message>)data;
                pd.dismiss();
                Intent intent = new Intent(TransiteActivity.this, MessageListMainActivity.class);
//                intent.pu(Config.MESSAGE_LIST_ALL,  messageList);
                startActivity(intent);
            }
        }, new FailCallback() {
            @Override
            public void onFail(int errCode) {
                switch (errCode){
                    case Config.STATUS_TOKEN_INVALID:
                        Toast.makeText(TransiteActivity.this,"token invalide", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TransiteActivity.this, GetCaptchaActivity.class));
                        break;
                    default:
                        Toast.makeText(TransiteActivity.this,"error", Toast.LENGTH_SHORT).show();
                        break;
                }
                pd.dismiss();
            }
        },phone_num,token);
    }
}
