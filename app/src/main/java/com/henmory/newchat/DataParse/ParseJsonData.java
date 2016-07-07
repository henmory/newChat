package com.henmory.newchat.DataParse;

import com.henmory.newchat.Main.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dan on 6/27/16.
 */
public class ParseJsonData {

    private String action_key;
    public static Map<String, Object> map = new HashMap<>();
    public ParseJsonData() {

    }
    public ParseJsonData(String action_key) {
        this.action_key = action_key;
    }

    public static void parse(String action_key, String data){
        if (null == action_key || data == null){
            return;
        }
        if (action_key.equals(Config.ACTION_LOGIN)){
            parseLoginReturnData(data);
        }else if (action_key.equals(Config.ACTION_UPLOAD_CONTRACT)){
            parseUploadContractReturnData(data);
        }
    }

    private static void parseLoginReturnData(String data){
        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject = new JSONObject();
            for (int i  = 0; i < jsonArray.length(); i++){
                jsonObject = (JSONObject) jsonArray.get(i);
                String status = (String) jsonObject.get(Config.KEY_STATUS);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private static void parseUploadContractReturnData(String data){

    }
}
