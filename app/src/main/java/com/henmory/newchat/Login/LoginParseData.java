package com.henmory.newchat.Login;

import com.henmory.newchat.Main.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dan on 6/28/16.
 */
public class LoginParseData  {

    private static String token;
    private static int status;


    public static String getToken() {
        return token;
    }

    public static int getStatus() {
        return status;
    }

    public static void parse(String result){
        try {
            JSONObject jsonObject = new JSONObject(result);
            status = jsonObject.getInt(Config.KEY_STATUS);
            if (Config.STATUS_SUCCESS == status){
                token = jsonObject.getString(Config.KEY_TOKEN);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
