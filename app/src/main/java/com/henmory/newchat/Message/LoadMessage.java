package com.henmory.newchat.Message;

import android.content.Context;

import com.henmory.newchat.Interface.FailCallback;
import com.henmory.newchat.Interface.SuccessCallback;
import com.henmory.newchat.Main.Config;
import com.henmory.newchat.NetWork.NetConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 6/28/16.
 */
public class LoadMessage {

    //input
    private String phone_num;
    private String token;
     //output
    private int status ;
    private List<Message> messageList = new ArrayList<>();

    public LoadMessage(String phone_num, String token, int status) {
        this.phone_num = phone_num;
        this.token = token;
        this.status = status;
    }
    public LoadMessage(Context context, final SuccessCallback successCallback,
                       final FailCallback failCallback, String phone_num, String token){
        this(phone_num,token, -1);
        new NetConnection(context, Config.KEY_ACTION,Config.ACTION_TIME_LINE, Config.KEY_PHONE_NUM, phone_num, Config.KEY_TOKEN, token) {
            @Override
            public void netConnectionSuccess(String data) {
                loadMessageSuccess(data);
                if (Config.STATUS_SUCCESS == status && successCallback != null){
                    successCallback.onSuccess(messageList);

                }else if(Config.STATUS_TOKEN_INVALID == status && failCallback!= null){
                    failCallback.onFail(Config.STATUS_TOKEN_INVALID);
                }else{
                    failCallback.onFail(Config.PARSE_ERROR);
                }
            }

            @Override
            public void netConnectionFail(int errorCode) {
                loadMessageFail(errorCode);
                if (failCallback != null){
                    failCallback.onFail(errorCode);
                }
            }
        };
    }


    public void loadMessageSuccess(String data){
         loadMessageParse(data);
     }
    public void loadMessageFail(int  errorCode){

    }

    private void loadMessageParse(String data){
        try {
            JSONObject jsonObject = new JSONObject(data);
            status = jsonObject.getInt(Config.KEY_STATUS);
            switch (status){
                case Config.STATUS_SUCCESS:
                    JSONArray jsonArray = jsonObject.getJSONArray(Config.ACTION_TIME_LINE);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = (JSONObject) jsonArray.get(i);
                        messageList.add(new Message(jsonObject.getString(Config.KEY_PHONE_NUM),
                                jsonObject.getInt(Config.KEY_MESSAGE_ID),
                                jsonObject.getString(Config.KEY_MESSAGE_CONTENT)));
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("load message parse error");
            status = Config.PARSE_ERROR;
        }
    }
}
