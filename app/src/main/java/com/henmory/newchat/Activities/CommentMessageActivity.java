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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.henmory.newchat.Comments.LoadComments;
import com.henmory.newchat.Comments.UploadComments;
import com.henmory.newchat.Common.CommonActivity;
import com.henmory.newchat.Interface.FailCallback;
import com.henmory.newchat.Interface.SuccessCallback;
import com.henmory.newchat.Main.Config;
import com.henmory.newchat.Comments.CommentAdapter;
import com.henmory.newchat.Comments.Comments;
import com.henmory.newchat.Message.Message;
import com.henmory.newchat.R;


import java.util.ArrayList;
import java.util.List;

public class CommentMessageActivity extends CommonActivity {

    private String token;
    private String native_phone_num;

//    message to be commented
    private TextView tv_commented_phone_num;
    private TextView tv_commented_msg_id;
    private TextView tv_commented_msg_content;
    private Message commented_message = new Message();


//    to be submit comments
    private Button btn_submit_comment;
    private EditText edit_submit_comment_content;
    private Comments comment_be_submited = new Comments();


//    already be commented
    private ListView lv;
    private List<Comments> mComents;
    private CommentAdapter commentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        Config.last_time = System.currentTimeMillis();
        System.out.println("CommentMessageActivity------------show comment tap = " + (Config.last_time - Config.middle_time));

        initView();
        initData();
        Config.middle_time = System.currentTimeMillis();
        System.out.println("CommentMessageActivity----------load comment tap = " + (Config.middle_time - Config.last_time));
    }

    void initView(){
        tv_commented_phone_num = (TextView) findViewById(R.id.message_item_phone_num_value);
        tv_commented_msg_id = (TextView) findViewById(R.id.message_item_msg_id_value);
        tv_commented_msg_content = (TextView) findViewById(R.id.message_item_msg_content);


        btn_submit_comment = (Button) findViewById(R.id.comment_message_btn_comment);
        edit_submit_comment_content = (EditText) findViewById(R.id.comment_message_edit_comment);


        lv = (ListView) findViewById(R.id.comment_lv);

        btn_submit_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitComment();
            }
        });
    }

    void initData(){

        token = Config.getToken(CommentMessageActivity.this);
        native_phone_num = Config.getCachePhoneNum(CommentMessageActivity.this);

        Intent intent = getIntent();
        commented_message.setPhoneNum(intent.getStringExtra(Config.KEY_PHONE_NUM));
        commented_message.setMsgId(intent.getIntExtra(Config.KEY_MESSAGE_ID, 0));
        commented_message.setMsgContent(intent.getStringExtra(Config.KEY_MESSAGE_CONTENT));
        tv_commented_phone_num.setText(commented_message.getPhoneNum());
        tv_commented_msg_content.setText(commented_message.getMsgContent());
        tv_commented_msg_id.setText(commented_message.getMsgId() + "");

        //from server
        loadComments();

    }


    private void loadComments(){

        System.out.println("CommentMessageActivity------load comments");
        System.out.println("CommentMessageActivity------to be commented message = " + commented_message.toString());
        new LoadComments(CommentMessageActivity.this, new SuccessCallback() {
            @Override
            public void onSuccess(Object data) {
                mComents = (List<Comments>)data;
                commentAdapter = new CommentAdapter(mComents, CommentMessageActivity.this, R.layout.comment_item);
                lv.setAdapter(commentAdapter);
                System.out.println("CommentMessageActivity------load comments sucessfully, and datas = " + mComents.toString());

            }
        }, new FailCallback() {
            @Override
            public void onFail(int errCode) {
                System.out.println("CommentMessageActivity------load comments failed");
                System.out.println("CommentMessageActivity----message load data failed errorCode = " + errCode);
                switch (errCode){
                    case Config.STATUS_TOKEN_INVALID:
                        Snackbar.make(lv,"token invalide", Snackbar.LENGTH_SHORT).show();
                        startActivity(new Intent(CommentMessageActivity.this, GetCaptchaActivity.class));
                        break;
                    case Config.NET_CONNECTION_ESTABLISH_FAIL:
                        Snackbar.make(lv,"establish failed", Snackbar.LENGTH_SHORT).show();
                        Intent intent = new Intent(CommentMessageActivity.this, MessageListMainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//parent activity will not execute oncreate function
                        startActivity(intent);
                        break;
                    default:
                        Snackbar.make(lv,"error", Snackbar.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(CommentMessageActivity.this, MessageListMainActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//parent activity will not execute oncreate function
                        startActivity(intent1);
                        break;
                }
                finish();
            }
        },commented_message.getPhoneNum(),token,commented_message.getMsgId());


    }

    void submitComment(){
        System.out.println("CommentMessageActivity---submit comment");
        String content = edit_submit_comment_content.getText().toString();
        if (TextUtils.isEmpty(content)){
            Toast.makeText(CommentMessageActivity.this, "null", Toast.LENGTH_SHORT).show();
            return;
        }else{
            System.out.println("CommentMesageActivity---submit comment datas = " + comment_be_submited.toString());
            comment_be_submited.setContent(content);
            comment_be_submited.setTime("4:29");
            comment_be_submited.setPhoneNum(native_phone_num);
            uploadComment(commented_message, comment_be_submited);
        }
    }

    private void uploadComment(Message message, Comments comment){
        final ProgressDialog progressDialog = new ProgressDialog(CommentMessageActivity.this, ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("upload comments, waiting...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        System.out.println("CommentMessageActivity---upload comment to server");
        System.out.println("CommentMessageActivity---to be commented message = " + message.toString());
        System.out.println("CommentMessageActivity---comment = " + comment.toString());

        new UploadComments(CommentMessageActivity.this, message, comment) {
            @Override
            public void uploadCommentSuccessfully() {
                commentAdapter.add(comment_be_submited);
                edit_submit_comment_content.setText("");
                progressDialog.dismiss();
                System.out.println("CommentMessageActivity--successfuly -upload comment to server ");

            }

            @Override
            public void uploadCommentFailly(int errorCode) {
                Toast.makeText(CommentMessageActivity.this, "upload error", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                System.out.println("CommentMessageActivity---upload comment to server unsuccessfully");
            }
        };
    }
}
