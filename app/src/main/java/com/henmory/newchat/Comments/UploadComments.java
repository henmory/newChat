package com.henmory.newchat.Comments;

import android.content.Context;

import com.henmory.newchat.Main.Config;
import com.henmory.newchat.Message.Message;
import com.henmory.newchat.NetWork.NetConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dan on 7/6/16.
 */
public abstract class UploadComments {

    private Message message;
    private Comments comment;

    private int status;

    public UploadComments(Message message, Comments comment, int status) {
        this.message = message;
        this.comment = comment;
        this.status = status;
    }

    public UploadComments(Context context, Message message, Comments comment){
        this(message,comment,-1);
        new NetConnection(context, Config.KEY_ACTION, Config.KEY_UPLOAD_COMMENT,
                Config.KEY_PHONE_NUM, message.getPhoneNum(),Config.KEY_MESSAGE_ID, message.getMsgId() + "",
                Config.KEY_PHONE_NUM, comment.getPhoneNum(),Config.KEY_COMMENT_CONTENT, comment.getTime(), Config.KEY_COMMENT_CONTENT, comment.getContent()) {
            @Override
            public void netConnectionSuccess(String data) {
                uploadCommentsParse(data);
                switch (status){
                    case Config.STATUS_SUCCESS:
                        uploadCommentSuccessfully();
                        break;
                    case Config.STATUS_TOKEN_INVALID:
                        uploadCommentFailly(status);
                        break;
                    default:
                        uploadCommentFailly(status);
                        break;
                }
            }

            @Override
            public void netConnectionFail(int errorCode) {
                uploadCommentFailly(status);
            }
        };
    }

    private void uploadCommentsParse(String data){
        try {
            JSONObject jsonObject = new JSONObject(data);
            status = jsonObject.getInt(Config.KEY_STATUS);
            switch (status){
                case Config.STATUS_SUCCESS:
                    break;
                case Config.STATUS_TOKEN_INVALID:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("upload comments parse error");
            status = Config.PARSE_ERROR;
        }
    }

    public abstract void uploadCommentSuccessfully();
    public abstract void uploadCommentFailly(int errorCode);
}
