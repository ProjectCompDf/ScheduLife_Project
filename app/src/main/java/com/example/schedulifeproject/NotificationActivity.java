package com.example.schedulifeproject;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class NotificationActivity extends AppCompatActivity {

    private EditText editTextMessage;
    private Button btnSendNotification, btnScheduleNotification;

    public static final String CHANNEL_ID = "NOTIFICATION_CHANNEL_ID";
    private static final int NOTIFICATION_ID = 2;
    private static final String CHANNEL_NAME = "NOTI_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        editTextMessage = findViewById(R.id.editText);
        btnSendNotification = findViewById(R.id.button);
        btnScheduleNotification = findViewById(R.id.button2);

        btnSendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });

        btnScheduleNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker();
            }
        });
    }

    private void sendNotification() {
        String message = editTextMessage.getText().toString().trim();

        if (!message.isEmpty())
        {
            createNotificationChannel();

            Intent notificationIntent = new Intent(this, NotificationReceiver.class);
            notificationIntent.putExtra("message", message);
            notificationIntent.putExtra("channel_id",CHANNEL_ID);
            notificationIntent.putExtra("notification_id",NOTIFICATION_ID);


            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this,
                    (int) System.currentTimeMillis(),
                    notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

            try {
                pendingIntent.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void openTimePicker() {

        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        scheduleNotification(hourOfDay, minute);
                    }
                }, hour, minute, false);

        timePickerDialog.show();
    }

    private void scheduleNotification(int hour, int minute) {
        String message = editTextMessage.getText().toString();

        if (!message.isEmpty()) {
            createNotificationChannel();

            Intent notificationIntent = new Intent(this, NotificationReceiver.class);
            notificationIntent.putExtra("message", message);
            notificationIntent.putExtra("channel_id", CHANNEL_ID);
            notificationIntent.putExtra("notification_id", NOTIFICATION_ID);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

            long futureInMillis = calculateFutureTime(hour, minute);

            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);

            Toast.makeText(this, "Notification scheduled at " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private long calculateFutureTime(int hour, int minute) {
        final Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        long futureInMillis = System.currentTimeMillis();

        if ((hour < currentHour) || (hour == currentHour && minute <= currentMinute)) {

            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTimeInMillis();
    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }

}