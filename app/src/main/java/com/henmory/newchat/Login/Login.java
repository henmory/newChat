package com.henmory.newchat.Login;

import android.content.Context;

import com.henmory.newchat.Activities.LoginActivity;
import com.henmory.newchat.DataParse.ParseJsonData;
import com.henmory.newchat.Interface.FailCallback;
import com.henmory.newchat.Interface.SuccessCallback;
import com.henmory.newchat.Main.Config;
import com.henmory.newchat.NetWork.NetConnection;

/**
 * Created by dan on 6/23/16.
 * handler login action, parse datas
 */
public class Login {

    private String phone_num; //input
    private String captcha;//input

    private String token;//output
    private int status;//output

    public Login(String phone_num, String captcha, String token, int status) {
        this.phone_num = phone_num;
        this.captcha = captcha;
        this.token = token;
        this.status = status;
    }

    public Login(Context context, final SuccessCallback successCallback, final FailCallback failCallback, String phone_num, String captcha) {
        this(phone_num, captcha, null, -1);
        new NetConnection(context, Config.KEY_ACTION, Config.ACTION_LOGIN, Config.KEY_PHONE_NUM, phone_num, Config.KEY_CPTCHA, captcha) {

            @Override
            public void netConnectionSuccess(String data) {
                loginSuccess(data);
                if ((null != successCallback) && (Config.STATUS_SUCCESS == status)){
                    successCallback.onSuccess(token);
                }else{
                    failCallback.onFail(Config.PARSE_ERROR);
                }
            }

            @Override
            public void netConnectionFail(int errorCode) {
                loginFailed(errorCode);
                if (null != failCallback){
                    failCallback.onFail(errorCode);
                }
            }
        };
    }

    private void loginSuccess(String data){
        LoginParseData.parse(data);
        token = LoginParseData.getToken();
        status = LoginParseData.getStatus();
        System.out.println(toString());
    }

    private void loginFailed(int errorCode){
        status = errorCode;
        token = null;

    }

    @Override
    public String toString() {
        return "Login.java:" + " token = " + token + " status = " + status;
    }
}
