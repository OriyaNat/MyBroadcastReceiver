package com.example.mybroadcastreceiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class Notification extends BroadcastReceiver {


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        CharSequence intentData = intent.getCharSequenceExtra("ClientName");
        /**
         * Ezra code with changes
         */

        String id = "channel_1";//id of channel
        String description = "accepted offer";//Description information of channel
        int importance = NotificationManager.IMPORTANCE_DEFAULT;//The Importance of channel
        NotificationChannel channel = new NotificationChannel(id, description, importance);//Generating channel

        // prepare intent which is triggered if the
        // notification is selected
        Intent intent4 = new Intent(Intent.ACTION_VIEW);
        intent4.setComponent(new ComponentName("com.example.travel_app_secondapp",
                "com.example.travel_app_secondapp.ui.LoginActivity"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent4, 0);

        android.app.Notification.Builder mBuilder = new android.app.Notification.Builder(context, id);
        mBuilder.setSmallIcon(R.mipmap.ic_bus)
                .setContentTitle("accepted offer")
                .setContentText("your suggest has been accepted by "+intentData)
                //TODO may change sound
                //.setSound(Uri.parse("android.resource://" +
                  //      context.getPackageName() + "/" + R.raw.birdssounds))
                .setContentIntent(pendingIntent);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                .build();




        // Gets an instance of the NotificationManager service

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        /*
        What Are Notification Channels?
        Notification channels enable us app developers to group our notifications into groups—channels—with
        the user having the ability to modify notification settings for the entire channel at once.
        For example, for each channel, users can completely block all notifications,
        override the importance level, or allow a notification badge to be shown.
        This new feature helps in greatly improving the user experience of an app.
        */

        mNotificationManager.createNotificationChannel(channel);
        mBuilder.setChannelId(id);

        /*
        When you issue multiple notifications about the same type of event,
        it’s best practice for your app to try to update an existing notification
        with this new information, rather than immediately creating a new notification.
        If you want to update this notification at a later date, you need to assign it an ID.
        You can then use this ID whenever you issue a subsequent notification.
        If the previous notification is still visible, the system will update this existing notification,
        rather than create a new one. In this example, the notification’s ID is 001
        */

        mNotificationManager.notify(001, mBuilder.build());
    }
}
