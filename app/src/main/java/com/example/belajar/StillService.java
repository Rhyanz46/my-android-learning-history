package com.example.belajar;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Timer;
import java.util.TimerTask;

public class StillService extends Service {
    Timer timer;
    TimerTask timerTask;
    String TAG = "StillService";
    int detik = 0, menit = 0;
    int Your_X_SECS = 5;
    Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);

        startTimer();

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");
        super.onCreate();
        context = this;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        stoptimertask();
        super.onDestroy();
    }

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();


    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
//        timer.schedule(timerTask, 5000, Your_X_SECS * 1000); //
//        timer.schedule(timerTask, 5000,1000); //
        timer.schedule(timerTask, 0,1000); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        detik++;
                        if (detik == 60){
                            menit++;
                            detik=0;
                        }

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "12");
                        builder.setSmallIcon(R.drawable.ic_stat_name);
                        builder.setContentTitle("Terhubung");
                        builder.setContentText("Sudah Hidup Selama : " + menit + " menit");
                        NotificationManagerCompat nm = NotificationManagerCompat.from(context);
                        nm.notify(0, builder.build());

                        Log.i(TAG, "run " + detik);
                    }
                });
            }
        };
    }
}
