package com.henmory.newchat.NetWork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.henmory.newchat.Comments.Comments;
import com.henmory.newchat.DataParse.ParseJsonData;
import com.henmory.newchat.Interface.FailCallback;
import com.henmory.newchat.Interface.SuccessCallback;
import com.henmory.newchat.Main.Config;
import com.henmory.newchat.Message.Message;


/**
 * Created by dan on 6/23/16.
 * 1.config http methord
 * 2.
 */
public abstract class NetConnection {

    public NetConnection(Context context, HttpMethord methord, String... params) {
        NetWorkConfig.REQUEST_METHORD = methord;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        String param = parseParams(params);
        System.out.println("net connection url_params =  \"" + param + "\"");


        if ((info != null) && (info.isConnected())) {
            new DownloadDataTask() {

                @Override
                public void taskOnSuccess(String data) {
                    netConnectionSuccess(data);
                }

                @Override
                public void taskOnFail(int errorCode) {
                    netConnectionFail(errorCode);
                }
            }.execute(param);
        } else {
            netConnectionFail(Config.NET_CONNECTION_ESTABLISH_FAIL);
        }

    }

    public NetConnection(Context context, String... params) {
        this(context, HttpMethord.POST, params);
    }




    private String parseParams(String... params){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.length; i += 2){
            sb.append(params[i]).append("=").append(params[i + 1]).append("&");
        }
        return sb.toString();
    }
    public abstract void netConnectionSuccess(String data);
    public abstract void netConnectionFail(int errorCode);


}

