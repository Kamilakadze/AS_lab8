package com.example.lab08;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lab8.R;

public class MainActivity2 extends AppCompatActivity {

    private Intent foregroundServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        foregroundServiceIntent = new Intent(this, MyService.class);

        Button startBtn = findViewById(R.id.button_start_music);
        Button stopBtn = findViewById(R.id.button_stop_music);

        startBtn.setOnClickListener(v ->
                ContextCompat.startForegroundService(MainActivity2.this, foregroundServiceIntent));

        stopBtn.setOnClickListener(v -> stopService(foregroundServiceIntent));
    }
}
