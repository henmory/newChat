package com.henmory.newchat.Main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dan on 6/20/16.
 */
public class Config {

    public static long start_time;
    public static long middle_time;
    public static long last_time;

    public final static String APP_ID = "com.henmory.newchar";
    public final static String MESSAGE_LIST_ALL = "message_list_all";
    public final static String MESSAGE_PUBLISH = "message_publish";
    public static final String MESSAGE_COMMENT = "message_comment";

    public final static String KEY_TOKEN = "token";
    public final static String KEY_PHONE_NUM = "phone_num";
    public final static String KEY_CPTCHA = "captcha";
    public final static String KEY_MESSAGE_ID = "msg_id";
    public final static String KEY_MESSAGE_CONTENT = "msg_content";
    public final static String KEY_STATUS = "status";
    public static final String KEY_COMMENT_TIME = "comment_time";
    public static final String KEY_COMMENT_CONTENT = "comment_content";
    public static final String KEY_UPLOAD_COMMENT = "upload_comment";
    public static final String KEY_MESSAGE_TO_COMMENTED = "message_to_be_commented";
    public static final Object KEY_COMMENT = "comment";


    /*
    * staus code from server
    * */
    public final static int STATUS_SUCCESS = 1;
    public final static int STATUS_FAILED = 0;
    public static final int STATUS_TOKEN_INVALID = 2;

    /**
     * parse failed
     * */
    public final static int PARSE_ERROR = -1;
    public final static int NET_CONNECTION_ESTABLISH_FAIL = -2;
    public final static int NET_CONNECTION_ERROR = -3;


    public final static String KEY_ACTION = "action";
    public final static String ACTION_LOGIN = "login";
    public static final String ACTION_UPLOAD_CONTRACT = "upload_contract";
    public static final String ACTION_TIME_LINE = "timeline";
    public static final String ACTION_PUBLISH_MESSAGE = "publish_message";
    public static final String ACTION_GET_COMMENTS = "get_comment";


//    public static  ProgressDialog global_progress_dialog;

    public static void cachePhoneNum(Context context, String str) {
        SharedPreferences.Editor editor = (SharedPreferences.Editor)context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_PHONE_NUM, str);
        editor.commit();
    }

    public static String getCachePhoneNum(Context context){
        SharedPreferences sp = context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE);
        return sp.getString(KEY_PHONE_NUM, null);
    }


    public static void cacheToken(Context context, String str) {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_TOKEN, str);
        editor.commit();
    }

    public static String getToken(Context context){
        SharedPreferences sp = context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE);
        return sp.getString(KEY_TOKEN, null);
    }
}
