package com.example.belajar;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.lang.ref.WeakReference;
import java.util.Random;

public class BackgroundTasks extends AsyncTask<Void, Void, Integer> {
    private WeakReference<Integer> jumlah;
    private Context context;

    BackgroundTasks(Integer counter, Context contextIn) {
        jumlah = new WeakReference<>(counter);
        context = contextIn;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        int hasil = 0;
        while (hasil != 100) {
            hasil++;
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "12");
            builder.setSmallIcon(R.drawable.ic_stat_name);
            builder.setContentTitle("Connection status");
            builder.setContentText("Selamat Datang : " + hasil);


            NotificationManagerCompat nm = NotificationManagerCompat.from(context);
            nm.notify(0, builder.build());
            Log.i("doInBackground", String.valueOf(hasil));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Generate a random number between 0 and 10
        Random r = new Random();
        int n = r.nextInt(11);

        // Make the task take long enough that we have
        // time to rotate the phone while it is running
        int s = n * 200;


        // Sleep for the random amount of time
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return s;
    }

    @Override
    protected void onPostExecute(Integer result) {
        Log.i("onPostExecute", String.valueOf(result));
    }
}
