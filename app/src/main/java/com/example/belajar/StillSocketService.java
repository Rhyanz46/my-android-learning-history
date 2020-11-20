package com.example.belajar;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class StillSocketService extends Service {
    private OkHttpClient client;
    Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notifMe();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void notifMe() {
        Request request = new Request.Builder().url("ws://172.20.10.3:9091/socket?token=444").build();

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
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @org.jetbrains.annotations.Nullable Response response) {
                super.onFailure(webSocket, t, response);
                Log.i("Socket onFailure", "onFailure terjadi");
                Log.i("Socket onFailure", t.toString());
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                super.onMessage(webSocket, text);
                sendNotification();
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
            Toast.makeText(this, "notif is started", Toast.LENGTH_SHORT).show();
            sendNotification();
            Log.i("Setting Background", "notif is started");
        }catch (Exception ignored){
            Toast.makeText(this, "failed to start notif", Toast.LENGTH_SHORT).show();
        };
    }

    private void sendNotification(){
        Toast.makeText(this, "notif is started", Toast.LENGTH_SHORT).show();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "12");
        builder.setSmallIcon(R.drawable.ic_stat_name);
        builder.setContentTitle("Terhubung");
        builder.setContentText("Hello Bro");
        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
        nm.notify(0, builder.build());
    }
}