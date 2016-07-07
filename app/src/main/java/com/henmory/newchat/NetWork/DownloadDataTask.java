package com.henmory.newchat.NetWork;

import android.os.AsyncTask;

import com.henmory.newchat.Main.Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dan on 6/24/16.
 * this class is to load data from http server
 * use this methord should implements two abstract methords:onFail onSuccess
 * @parm
 */
public abstract class DownloadDataTask extends AsyncTask<String, Integer, String> {

    private int content_length;


    public DownloadDataTask() {
        content_length = 0;
    }

    @Override
    protected String doInBackground(String... params) {
        return downloadData(params[0]);// TODO: 6/24/16 why
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
        if (string == null) {
            System.out.println("DownloadDataTask error");
            taskOnFail(Config.NET_CONNECTION_ERROR);
        } else{ //has datas, but probably results is wrong
            System.out.println("DownloadDataTask download success,and data = " + string);
            taskOnSuccess(string);
        }
    }

    private String downloadData(String str) {

        HttpURLConnection httpURLConnection = null;
        try {
            URL url = null;

            switch (NetWorkConfig.REQUEST_METHORD) {
                case POST:
                    url = new URL(NetWorkConfig.SERVER_IP);
                    break;
                case GET:
                    url = new URL(NetWorkConfig.SERVER_IP + "?" + str);
                    break;
            }
            System.out.println("http request methord = " + NetWorkConfig.REQUEST_METHORD);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setChunkedStreamingMode(0);
            httpURLConnection.setReadTimeout(NetWorkConfig.READ_TIMEOUT);
            httpURLConnection.setConnectTimeout(NetWorkConfig.CONNECT_TIMEOUT);
            httpURLConnection.setRequestMethod(NetWorkConfig.REQUEST_METHORD.toString());
            httpURLConnection.setDoInput(true);
//post methord  should write data to the server
            if (NetWorkConfig.REQUEST_METHORD == HttpMethord.POST) {
                httpURLConnection.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                bufferedWriter.write(str);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStreamWriter.close();
                System.out.println("post data = " + str);

            }

            content_length = httpURLConnection.getContentLength();
            System.out.println("data length = " + content_length);
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(inputStreamReader);
                String string;
                StringBuilder sb = new StringBuilder();
                while ((string = br.readLine()) != null) {
                    sb.append(string);
                }
                br.close();
                inputStreamReader.close();
                inputStream.close();
                return sb.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
            taskOnFail(Config.NET_CONNECTION_ERROR);
        }
        return null;
    }

    public abstract void taskOnSuccess(String data);
    public abstract void taskOnFail(int errorCode);
}
