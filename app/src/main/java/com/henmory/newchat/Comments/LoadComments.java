package com.henmory.newchat.Comments;

import android.content.Context;

import com.henmory.newchat.Interface.FailCallback;
import com.henmory.newchat.Interface.SuccessCallback;
import com.henmory.newchat.Main.Config;
import com.henmory.newchat.Message.Message;
import com.henmory.newchat.NetWork.NetConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 6/28/16.
 */
public class LoadComments {

    //input
    private String phone_num;
    private String token;
    private int msg_id;
     //output
    private int status ;
    private List<Comments> commentsList = new ArrayList<>();

    public LoadComments(String phone_num, String token, int msg_id, int status) {
        this.phone_num = phone_num;
        this.token = token;
        this.msg_id = msg_id;
        this.status = status;
    }
    public LoadComments(Context context, final SuccessCallback successCallback,
                        final FailCallback failCallback, String phone_num, String token, int msg_id){
        this(phone_num,token, -1, -1);
        new NetConnection(context, Config.KEY_ACTION,Config.ACTION_GET_COMMENTS, Config.KEY_PHONE_NUM, phone_num,
                Config.KEY_TOKEN, token, Config.KEY_MESSAGE_ID, msg_id + "") {
            @Override
            public void netConnectionSuccess(String data) {
                loadCommentsParse(data);
                if (Config.STATUS_SUCCESS == status && successCallback != null){
                    successCallback.onSuccess(commentsList);

                }else if(Config.STATUS_TOKEN_INVALID == status && failCallback!= null){
                    failCallback.onFail(Config.STATUS_TOKEN_INVALID);
                }else{
                    failCallback.onFail(Config.PARSE_ERROR);
                }
            }

            @Override
            public void netConnectionFail(int errorCode) {
                if (failCallback != null){
                    failCallback.onFail(errorCode);
                }
            }
        };
    }




    private void loadCommentsParse(String data){
        try {
            JSONObject jsonObject = new JSONObject(data);
            status = jsonObject.getInt(Config.KEY_STATUS);
            switch (status){
                case Config.STATUS_SUCCESS:
                    JSONArray jsonArray = jsonObject.getJSONArray(Config.ACTION_GET_COMMENTS);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = (JSONObject) jsonArray.get(i);
                        commentsList.add(new Comments(jsonObject.getString(Config.KEY_PHONE_NUM),
                                jsonObject.getString(Config.KEY_COMMENT_CONTENT),
                                jsonObject.getString(Config.KEY_COMMENT_TIME)));
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("load comments parse error");
            status = Config.PARSE_ERROR;
        }
    }
}
