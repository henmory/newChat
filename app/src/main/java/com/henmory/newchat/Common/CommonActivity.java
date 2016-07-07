package com.henmory.newchat.Common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dan on 16/6/12.
 *
 * all activities extend this class,and then CommonActivityManger will manage activity automatically
 */
public class CommonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonActivityManager.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommonActivityManager.finishActivity(this);
    }
}
