package com.henmory.newchat.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.henmory.newchat.Common.CommonActivity;
import com.henmory.newchat.Files.CacheFile;
import com.henmory.newchat.Interface.FailCallback;
import com.henmory.newchat.Interface.SuccessCallback;
import com.henmory.newchat.Main.Config;
import com.henmory.newchat.Message.LoadMessage;
import com.henmory.newchat.Message.Message;
import com.henmory.newchat.Message.MessageAdapter;
import com.henmory.newchat.R;

import java.util.ArrayList;
import java.util.List;

public class MessageListMainActivity extends CommonActivity {

    private String phone_num;
    private String token;
    private List<Message> messageList = new ArrayList<>();
    private MessageAdapter messageAdapter = new MessageAdapter(messageList, this, R.layout.message_item);
    private ListView lv;

    private ListView lv_navigation;
    private ActionBarDrawerToggle drawerToggle; //为actionbar设置的事件监听器
    private DrawerLayout mDrawerLayout; //真个布局

    private TextView tv_content_null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list_main);
        initNavigation();

        tv_content_null = (TextView) findViewById(R.id.content_message_list_main_content_is_null);
        lv = (ListView) findViewById(R.id.message_list_main_lv);
        lv.setAdapter(messageAdapter);

        //to comment activity
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                btnClickedCommentMessage(parent,view,position,id);
            }
        });

        loadData();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnClickedPublishMessage();
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


//    当采用singleTop模式启动activity时，再次进入该activity，采用FLAG_ACTIVITY_CLEAR_TOP模式进入，会在
//    onnewIntent中传递数据
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Message message = (Message) intent.getSerializableExtra(Config.MESSAGE_PUBLISH);
        if (null != message){
            System.out.println("MessageListMainActivity----onNewIntent ---message = " + message.toString());
            messageAdapter.add(message);
        }
    }

    //   get main message datas from server
    private void loadData(){

        Intent i = getIntent();
        phone_num = i.getStringExtra(Config.KEY_PHONE_NUM);
        token = i.getStringExtra(Config.KEY_TOKEN);
        System.out.println("MessageListMainActivity----phone_num = " + phone_num + "; token = " + token);
        new LoadMessage(MessageListMainActivity.this, new SuccessCallback() {
            @Override
            public void onSuccess(Object data) {
                messageList = (ArrayList<Message>)data;
                if (messageList.size() > 0){
                    CacheFile.cacheTempDatas(MessageListMainActivity.this, messageList);
                    messageAdapter.addListElements(messageList);
                    System.out.println("MessageListMainActivity----message load data successfully");
                }else{
                    tv_content_null.setText("content is null");
                    System.out.println("tmp datas is null");
                }

            }
        }, new FailCallback() {
            @Override
            public void onFail(int errCode) {

                Object o = CacheFile.readTmpDats(MessageListMainActivity.this);
                System.out.println("MessageListMainActivity----message load data failed, and load tmp datas from file");
                if (o != null){
                    messageList = (ArrayList<Message>)o;
                    messageAdapter.addListElements(messageList);
                    System.out.println("tmp datas = " + messageList);
                }else{
                    tv_content_null.setText("content is null");
                    System.out.println("tmp datas is null");
                }
                switch (errCode){
                    case Config.STATUS_TOKEN_INVALID:
                        Snackbar.make(findViewById(R.id.CoordinatorLayout),"token invalide", Snackbar.LENGTH_SHORT).show();
                        startActivity(new Intent(MessageListMainActivity.this, GetCaptchaActivity.class));
                        finish();
                        break;
                    case Config.NET_CONNECTION_ESTABLISH_FAIL:
                        Snackbar.make(findViewById(R.id.CoordinatorLayout),"establish failed", Snackbar.LENGTH_SHORT).show();
                        break;
                    default:
                        Snackbar.make(findViewById(R.id.CoordinatorLayout),"error", Snackbar.LENGTH_SHORT).show();
                        System.out.println("MessageListMainActivity----message load data failed errorCode = " + errCode);

                        break;
                }
            }
        },phone_num,token);
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        int id =  item.getItemId();
        if (id == R.id.item_messsage_list_publish_message){
            btnClickedPublishMessage();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    void initNavigation(){
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv_navigation = (ListView) findViewById(R.id.get_captcha_left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //整个布局的id

        //add navigation datas
        String[] datas = getResources().getStringArray(R.array.navigation_draver);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1, datas);
        lv_navigation.setAdapter(adapter);
        lv_navigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
            }
        });

        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerLayout.addDrawerListener(drawerToggle);//设置监听

    }

    private void btnClickedPublishMessage(){
        Intent intent = new Intent(MessageListMainActivity.this, PublishActivity.class);
        intent.putExtra(Config.KEY_PHONE_NUM, phone_num);
        intent.putExtra(Config.KEY_TOKEN,token);
        startActivity(intent);
        System.out.println("MessageListMainActivity---- go to publish message activity");
    }

    private void btnClickedCommentMessage(AdapterView<?> parent, View view, int position, long id){
        Config.start_time = System.currentTimeMillis();
        Message message = messageList.get(position);
        System.out.println("MessageListMainActivity--------to be commented message = " + message);
        Intent intent = new Intent(MessageListMainActivity.this, CommentMessageActivity.class);
        intent.putExtra(Config.KEY_PHONE_NUM, message.getPhoneNum());
        intent.putExtra(Config.KEY_MESSAGE_CONTENT,message.getMsgContent());
        intent.putExtra(Config.KEY_MESSAGE_ID,message.getMsgId());
        startActivity(intent);
        Config.middle_time = System.currentTimeMillis();
        System.out.println("MessageListMainActivity----------- start activity tap = " + (Config.middle_time - Config.start_time));
    }

    private void messageListMainActivityContentIsNull(){
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.content_message_list_main_root_layout);
        TextView textView = new TextView(this);
        textView.setText("content is null");
        textView.setLayoutParams(new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        relativeLayout.addView(textView);
    }

}
