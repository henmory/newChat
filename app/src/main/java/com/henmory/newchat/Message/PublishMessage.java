package com.henmory.newchat.Message;

import android.content.Context;

import com.henmory.newchat.Interface.FailCallback;
import com.henmory.newchat.Interface.SuccessCallback;
import com.henmory.newchat.Main.Config;
import com.henmory.newchat.NetWork.NetConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dan on 6/28/16.
 */
public class PublishMessage {

    //input
    private String token;
    private Message message; //unuseful

    //output
    private int status;

    public PublishMessage(int status) {
        this.status = status;
    }

    public PublishMessage(Context context, final SuccessCallback successCallback, final FailCallback failCallback, String token, String phone_num, String message_content) {
        this(-1);
        new NetConnection(context, Config.KEY_ACTION,Config.ACTION_PUBLISH_MESSAGE, Config.KEY_PHONE_NUM, phone_num, Config.KEY_MESSAGE_CONTENT, message_content) {

            @Override
            public void netConnectionSuccess(String data) {
                parseData(data);
                if (Config.STATUS_SUCCESS == status && successCallback != null){
                    successCallback.onSuccess(null);

                }else if(Config.STATUS_TOKEN_INVALID == status && failCallback!= null){
                    failCallback.onFail(Config.STATUS_TOKEN_INVALID);
                }else{
                    failCallback.onFail(Config.PARSE_ERROR);
                }
            }

            @Override
            public void netConnectionFail(int errorCode) {
                failCallback.onFail(errorCode);
            }
        };
    }

    private void parseData(String data){
        try {
            JSONObject jsonObject = new JSONObject(data);
            status = jsonObject.getInt(Config.KEY_STATUS);
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("publish message parse error");
            status = Config.PARSE_ERROR;
        }
    }
}
