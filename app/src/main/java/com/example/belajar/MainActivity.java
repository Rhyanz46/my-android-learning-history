package com.example.belajar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//https://developer.android.com/courses/fundamentals-training/toc-v2

public class MainActivity extends AppCompatActivity {
    private int counted;
    private TextView count_view;
    private Boolean showInfo = true;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static final int TEXT_REQUEST = 1;
    public static final  String EXTRA_MESSAGE = "com.example.belajar.extra.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Log.i("state", "Hello");
        } else {
            counted = savedInstanceState.getInt("last_value");
            Log.i("state", "Hallo kembali");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.belajar);

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                incrementCounter();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("last_value", counted);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            if (requestCode == TEXT_REQUEST) {
                if (resultCode == RESULT_OK) {
                    String last_value = intent.getStringExtra(Settings.EXTRA_LAST_VALUE);
                    TextView t = findViewById(R.id.info);
                    t.setText("terakhir : " + last_value);
                }
            }
        }
    }


    public void showToast(View v){
        TextView count_view = findViewById(R.id.textView);
        count_view.setText("Noted");
        Toast toast = Toast.makeText(this, "keren kali", Toast.LENGTH_SHORT);
        toast.show();
        Log.i("toast", "munculin toast");
        Intent intent = new Intent(this, Settings.class);
        intent.putExtra(EXTRA_MESSAGE, Integer.toString(counted));
        startActivityForResult(intent, TEXT_REQUEST);
    }

    public void add_new_activity(View v){
        TextView t = findViewById(R.id.info);
        if(showInfo){
            t.setVisibility(View.VISIBLE);
        }else{
            t.setVisibility(View.INVISIBLE);
        }
        showInfo = !showInfo;
        Log.i(LOG_TAG, "add_new_activity");
    }

    public void incrementCounter() {
        counted++;
        count_view = findViewById(R.id.textView);
        count_view.setText(Integer.toString(counted));
    }

    public void reset(View v) {
        Log.i("reset", "do reset");
        counted = -1;
    }
}