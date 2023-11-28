package com.example.schedulifeproject;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class ServiceActivity extends Service {

    private BroadcastReceiver batteryReceiver;
    public static final String CHANNEL_ID = "battery_channel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        startForeground(1, createNotification());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        registerBatteryReceiver();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Battery Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void registerBatteryReceiver() {
        batteryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

                float batteryPercentage = (level / (float) scale) * 100;

                if (batteryPercentage <= 5) {
                    showLowBatteryNotification();
                }
            }
        };

        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);
    }

    private void showLowBatteryNotification() {
        Intent notificationIntent = new Intent(this, NotificationReceiver.class);
        notificationIntent.putExtra("message", "Battery is at 5% or lower.");
        notificationIntent.putExtra("channel_id", CHANNEL_ID);

        PendingIntent pendingIntent = PendingIntent.getBroadcast
        (
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        try
        {
            pendingIntent.send();
        }
        catch (PendingIntent.CanceledException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryReceiver);
    }

    private Notification createNotification() {
        Intent notificationIntent = new Intent(this, ServiceActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent stopServiceIntent = new Intent(this, NotificationReceiver.class);
        stopServiceIntent.setAction("STOP_SERVICE");
        PendingIntent stopServicePendingIntent = PendingIntent.getBroadcast(
                this,
                1,
                stopServiceIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Action stopServiceAction = new NotificationCompat.Action.Builder(
                android.R.drawable.ic_menu_close_clear_cancel,
                "Stop Service",
                stopServicePendingIntent
        ).build();

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Battery Service")
                .setContentText("Monitoring battery status.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .addAction(stopServiceAction)
                .setOngoing(false)
                .build();
    }
}