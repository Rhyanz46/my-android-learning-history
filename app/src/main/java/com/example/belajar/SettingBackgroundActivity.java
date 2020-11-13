package com.example.belajar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SettingBackgroundActivity extends AppCompatActivity {
    private static final int NOTIFICATION_ID = 1;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotifyManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);
        createNotificationChannel();
//        new BackgroundTasks(1, this).execute();
    }

    public void notifMe(View view) {
        try {
            Intent intent = new Intent(this, StillService.class);
            startService(intent);

            Toast.makeText(this, "notif is started", Toast.LENGTH_SHORT).show();
            sendNotification();
            Log.i("Setting Background", "notif is started");
        }catch (Exception ignored){
            Toast.makeText(this, "failed to start notif", Toast.LENGTH_SHORT).show();
        };
    }

    public void sendNotification() {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    };

    public void createNotificationChannel() {
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Create a NotificationChannel
            NotificationChannel notificationChannel = new NotificationChannel(
                    PRIMARY_CHANNEL_ID,
                    "Mascot Notification", NotificationManager
                    .IMPORTANCE_HIGH
            );
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    private NotificationCompat.Builder getNotificationBuilder(){
        Intent notificationIntent = new Intent(this, SettingBackgroundActivity.class);
//        finishAffinity();
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentIntent(notificationPendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);
        return notifyBuilder;

    };

}