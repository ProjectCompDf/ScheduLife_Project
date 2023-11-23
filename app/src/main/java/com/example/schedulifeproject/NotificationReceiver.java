package com.example.schedulifeproject;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String DEFAULT_CHANNEL_ID = "DEFAULT_CHANNEL_ID";
    private static final String CHANNEL_NAME = "DEFAULT_NOTI";

    public static final String CHANNEL_ID_EXTRA = "channel_id";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
        String channelId = intent.getStringExtra(CHANNEL_ID_EXTRA);

        if (channelId == null) {

            channelId = NotificationActivity.CHANNEL_ID;
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Scheduled Notification")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider handling the case where permission is not granted.
            return;
        }

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }


}
