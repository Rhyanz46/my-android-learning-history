package com.example.belajar;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class StillSocketService extends Service {
    private OkHttpClient client;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            SocketServiceDriver();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void SocketServiceDriver() throws InterruptedException {
//        Request request = new Request.Builder().url("ws://localhost:9091/socket?token=444").build();
//        CustomWebSocket listener = new CustomWebSocket();
//        WebSocket ws = client.newWebSocket(request, listener);
//        client.dispatcher().executorService().shutdown();
        for (int i = 0; i < 100; i++){
            TimeUnit.SECONDS.sleep(1);
            Log.i("StillSeriveSocket", "hitung : " + i);
        }
    }
}