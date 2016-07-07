package com.henmory.newchat.CustomView;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.henmory.newchat.Activities.GetCaptchaActivity;
import com.henmory.newchat.Activities.MessageListMainActivity;
import com.henmory.newchat.Activities.SpecialActivity;
import com.henmory.newchat.R;

/**
 * Created by dan on 6/22/16.
 */
public class MyNotification {

    /*
    * this methord is used when the activity that we want to entry by notifycation is a regular activity
    * */
    public static void showNotification(Context context){


        Bitmap large = BitmapFactory.decodeResource(context.getResources(), R.drawable.large);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(android.R.drawable.ic_notification_overlay);
        mBuilder.setLargeIcon(large);
        mBuilder.setContentTitle("宝儿");
        mBuilder.setTicker("收到宝儿来的消息");
        mBuilder.setAutoCancel(true);
        mBuilder.setContentText("好无聊啊!");

        Intent intent = new Intent(context, MessageListMainActivity.class);
        //adds back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        //adds the intent to the top of stack
        stackBuilder.addParentStack(MessageListMainActivity.class);
        stackBuilder.addNextIntent(intent);
        //gets a pendingIntent containing the entire back stack
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);


        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int mNotificationId = 0;
        manager.notify(mNotificationId,mBuilder.build());
    }


    /*
   * this methord is used when the activity that we want to entry by notifycation is a special activity
   * */
    public static void showNotification1(Context context) {
        Bitmap large = BitmapFactory.decodeResource(context.getResources(), R.drawable.large);
        // Instantiate a Builder object.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(android.R.drawable.ic_notification_overlay);
        builder.setLargeIcon(large);
        builder.setContentTitle("宝儿");
        builder.setTicker("收到宝儿来的消息");
        builder.setAutoCancel(true);
        builder.setContentText("好无聊啊!");
        // Creates an Intent for the Activity
        Intent intent = new Intent(context, SpecialActivity.class);
        // Sets the Activity to start in a new, empty task
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Creates the PendingIntent
        PendingIntent pendingIntent =  PendingIntent.getActivity(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        // Puts the PendingIntent into the notification builder
        builder.setContentIntent(pendingIntent);
        // Notifications are issued by sending them to the
        // NotificationManager system service.
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Builds an anonymous Notification object from the builder, and
        // passes it to the NotificationManager
        int id = 0;
        mNotificationManager.notify(id, builder.build());
    }
}
