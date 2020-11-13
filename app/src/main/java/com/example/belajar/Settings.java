package com.example.belajar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    public static final String EXTRA_LAST_VALUE = null;
    private int int_last_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        setContentView(R.layout.activity_settings);
        TextView jumlah = findViewById(R.id.jumlah);
        Intent intent = getIntent();
        String last_value = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        int_last_value = Integer.parseInt(last_value);
        jumlah.setText(last_value);

        TextView value = findViewById(R.id.jumlah);
        value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent background_intent = new Intent(Settings.this, SettingBackgroundActivity.class);
                startActivity(background_intent);
            }
        });
    }

    public void share(View v){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "total waktu saya adalah");
        shareIntent.setType("text/plain");

        if(shareIntent.resolveActivity(getPackageManager()) != null){
            startActivity(shareIntent);
        }
    }

    public void back(View v) {
        Log.i("CDA", "onBackPressed Called");
        Intent setIntent = new Intent();
        setIntent.putExtra(EXTRA_LAST_VALUE, Integer.toString(int_last_value));
        setResult(RESULT_OK, setIntent);
        finish();
    }
}