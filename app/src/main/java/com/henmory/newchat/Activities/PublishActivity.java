package com.henmory.newchat.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.henmory.newchat.Common.CommonActivity;
import com.henmory.newchat.Interface.FailCallback;
import com.henmory.newchat.Interface.SuccessCallback;
import com.henmory.newchat.Main.Config;
import com.henmory.newchat.Message.Message;
import com.henmory.newchat.Message.PublishMessage;
import com.henmory.newchat.NetWork.NetWorkConfig;
import com.henmory.newchat.R;

public class PublishActivity extends CommonActivity {

    private Button btn_publish;
    private EditText edit_publish_content;
    private Message message = new Message();

    private String phone_num;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        phone_num = getIntent().getStringExtra(Config.KEY_PHONE_NUM);
        token = getIntent().getStringExtra(Config.KEY_TOKEN);

        btn_publish = (Button) findViewById(R.id.publish_message_btn_comment);
        edit_publish_content = (EditText) findViewById(R.id.publish_message_edit_comment);

        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edit_publish_content.getText().toString();
                if (TextUtils.isEmpty(content)){
                    Snackbar.make(v, "be not null", Snackbar.LENGTH_SHORT).show();
                }else{
                    System.out.println("PublishActivity-----------publish message = " + message.toString());
                    message.setMsgContent(content);
                    message.setPhoneNum(phone_num);
                    message.setMsgId(0);
                    publishMessage();
                }
            }
        });
    }

    private void publishMessage(){
        final ProgressDialog progressDialog = new ProgressDialog(PublishActivity.this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("publishing, waiting...");
        progressDialog.show();
        new PublishMessage(PublishActivity.this, new SuccessCallback() {
            @Override
            public void onSuccess(Object string) {
                progressDialog.dismiss();
                Intent intent = new Intent(PublishActivity.this, MessageListMainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Config.MESSAGE_PUBLISH, message);
                System.out.println("publishActivity----publish message successfully and publish message = " + message.toString());
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//parent activity will not execute oncreate function
                startActivity(intent);
                finish();
            }
        }, new FailCallback() {
            @Override
            public void onFail(int errCode) {
                progressDialog.dismiss();
                switch (errCode){
                    case Config.STATUS_TOKEN_INVALID:
                        Toast.makeText(PublishActivity.this,"token invalide", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PublishActivity.this, GetCaptchaActivity.class));
                        break;
                    case Config.NET_CONNECTION_ESTABLISH_FAIL:
                        Toast.makeText(PublishActivity.this,"establish failed", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(PublishActivity.this,"error", Toast.LENGTH_SHORT).show();
                        System.out.println("publishActivity----publish message failed errorCode = " + errCode);

                        break;
                }
            }
        },token, phone_num, message.getMsgContent());
    }

}
