package com.henmory.newchat.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.henmory.newchat.Common.CommonActivity;
import com.henmory.newchat.Main.Config;
import com.henmory.newchat.R;

public class GetCaptchaActivity extends CommonActivity implements View.OnClickListener{

    private EditText editText_phone_num;
    private Button btn_get_captcha;
    private Button btn_fill_captcha;
    private ListView lv_navigation;

    private ActionBarDrawerToggle drawerToggle; //为actionbar设置的事件监听器
    private DrawerLayout mDrawerLayout; //真个布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_captcha);
        bindViews();
        initNavigation();
//        System.out.println("create");
        /*these codes have already implemented the click event of app icon
        if you want to implement simple functions, you can use these code.
        next codes are used to implement Navigation Drawer*/
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setLogo(R.mipmap.ic_launcher);
//        toolbar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println("ok");
//            }
//        });

    }

    private void bindViews(){
        editText_phone_num = (EditText) findViewById(R.id.get_captcha_tv_phone_num_value);
        btn_get_captcha = (Button) findViewById(R.id.get_captcha_btn_get_captcha);
        btn_fill_captcha = (Button) findViewById(R.id.get_captcha_btn_fill_captcha);

        btn_get_captcha.setOnClickListener(this);
        btn_fill_captcha.setOnClickListener(this);
        btn_fill_captcha.setEnabled(false);
        btn_get_captcha.setEnabled(false);

        editText_phone_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(editText_phone_num.getText().toString())){
                    btn_get_captcha.setEnabled(false);
                }else{
                    btn_get_captcha.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_captcha_btn_get_captcha:
                getCaptcha(v);
                break;
            case R.id.get_captcha_btn_fill_captcha:
                fillCaptcha(v);
                break;
        }
    }

    private void getCaptcha(View v){

        String phone_num = editText_phone_num.getText().toString();
        Config.cachePhoneNum(GetCaptchaActivity.this, phone_num);
//        ProgressDialog progressDialog = new ProgressDialog(GetCaptchaActivity.this, ProgressDialog.STYLE_SPINNER);
//        progressDialog.setMessage("验证码已发出，请注意查收");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        // TODO: 6/18/16 extract these to a single class
        CountDownTimer timer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btn_get_captcha.setText(getResources().getString(R.string.get_captcha) +
                        "(" + millisUntilFinished / 1000 + "秒)");
                btn_get_captcha.setEnabled(false);
            }
            @Override
            public void onFinish() {
                btn_get_captcha.setText(getResources().getString(R.string.get_captcha));
                btn_get_captcha.setEnabled(true);
                btn_fill_captcha.setEnabled(true);
            }

        };
        timer.start();

    }

    private void fillCaptcha(View v){
        startActivity(new Intent(GetCaptchaActivity.this, LoginActivity.class));

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

        return super.onOptionsItemSelected(item);
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
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
    }
}
