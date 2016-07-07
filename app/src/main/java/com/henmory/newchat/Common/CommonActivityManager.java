package com.henmory.newchat.Common;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dan on 7/6/16.
 */
public class CommonActivityManager {

    private static List<Activity> activityList = new ArrayList<>();


    private static void removeActivity(Activity activity){
        if (activity == null){
            return;
        }
        if (activityList != null){
            if (activityList.contains(activity)){
                activityList.remove(activity);
            }
        }
    }


    /**
    * called onCreate
    * */
    public static void addActivity(Activity activity){
        if (activityList == null){
            activityList = new ArrayList<>();
        }
        if (!activityList.contains(activity)){
            activityList.add(activity);
        }
    }

    /**
     * called onDestory
     * */
    public static void finishActivity(Activity activity){
        if (activity == null){
            return;
        }
        removeActivity(activity);
        activity.finish();
    }

    public static void finishAllActivity(){
        for (Activity activity:
             activityList) {
            finishActivity(activity);
        }
    }

    /**
    * this methord is used when we are running a activity, but to finish the other activity by class name
     * in a result activityManager will call the activities onDestory function to destory this activity,
     * and the acitivity will be removed in back stack and activityList which we define
     *
     * usage:CommonActivityManager.finishActivityByClassName(classname);
    * */
    public static void finishActivityByClassName(Class<?> name){
        Activity tmpActivity = null;
        for (Activity activity :
                activityList) {
            if (activity.getClass().equals(name)){
                tmpActivity = activity;
            }
        }
        finishActivity(tmpActivity);
    }

}
