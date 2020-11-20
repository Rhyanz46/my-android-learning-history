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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class SettingBackgroundActivity extends AppCompatActivity {
    private static final int NOTIFICATION_ID = 1;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotifyManager;
    private OkHttpClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background);
        createNotificationChannel();
//        new BackgroundTasks(1, this).execute();
    }

    public void notifMe(View view) {
        Request request = new Request.Builder().url("ws://45.64.97.77:9091/driver/v1/notification/order?token=444").build();

        new OkHttpClient().newWebSocket(request, new WebSocketListener() {
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosed(webSocket, code, reason);
                Log.i("Socket onClosed", "onClosed terjadi");
            }

            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosing(webSocket, code, reason);
                Log.i("Socket onClosing", "onClosing terjadi");
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
                Log.i("Socket onFailure", "onFailure terjadi");
                Log.i("Socket onFailure", t.toString());
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                super.onMessage(webSocket, text);
                sendNotification(text);
                Log.i("Socket onMessage", "onMessage String terjadi");
                Log.i("Socket onMessage", "message : " + text);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                super.onMessage(webSocket, bytes);
                Log.i("Socket onMessage", "onMessage byte terjadi");
                Log.i("Socket onMessage", "message : " + bytes.toString());
            }

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                super.onOpen(webSocket, response);
                Log.i("Socket onOpen", "onOpen terjadi");
                webSocket.send("hello server ku");
            }
        });
        try {
//            Intent intentStillService = new Intent(this, StillService.class);
//            startService(intentStillService);

//            Intent intentStillSocket = new Intent(this, StillSocketService.class);
//            startService(intentStillSocket);

            Toast.makeText(this, "notif is started", Toast.LENGTH_SHORT).show();
            sendNotification("Hello");
            Log.i("Setting Background", "notif is started");
        }catch (Exception ignored){
            Toast.makeText(this, "failed to start notif", Toast.LENGTH_SHORT).show();
        };
    }

    public void sendNotification(String message) {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder(message);
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

    private NotificationCompat.Builder getNotificationBuilder(String message){
        Intent notificationIntent = new Intent(this, SettingBackgroundActivity.class);
//        finishAffinity();
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("New Order!")
                .setContentText(message)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentIntent(notificationPendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);
        return notifyBuilder;

    };

}