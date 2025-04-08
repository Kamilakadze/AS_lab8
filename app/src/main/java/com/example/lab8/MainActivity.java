package com.example.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText randomCharEditText;
    private Intent backgroundServiceIntent;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomCharEditText = findViewById(R.id.editText_randomChar);
        backgroundServiceIntent = new Intent(this, RandomCharacterService.class);

        Button startBtn = findViewById(R.id.button_start);
        Button stopBtn = findViewById(R.id.button_stop);
        Button nextBtn = findViewById(R.id.button_next);

        startBtn.setOnClickListener(v -> {
            Log.i("MainActivity", "Нажата кнопка START");
            startService(backgroundServiceIntent);
        });

        stopBtn.setOnClickListener(v -> {
            Log.i("MainActivity", "Нажата кнопка STOP");
            stopService(backgroundServiceIntent);
            randomCharEditText.setText("");
        });

        nextBtn.setOnClickListener(v -> {
            Log.i("MainActivity", "Переход на MainActivity2");
            startActivity(new Intent(MainActivity.this, MainActivity2.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainActivity", "onStart — регистрируем BroadcastReceiver");
        IntentFilter filter = new IntentFilter("my.custom.action.tag.lab6");
        broadcastReceiver = new MyReceiver();
        registerReceiver(broadcastReceiver, filter, Context.RECEIVER_EXPORTED);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MainActivity", "onStop — отписываемся от BroadcastReceiver");
        unregisterReceiver(broadcastReceiver);
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            char c = intent.getCharExtra("randomCharacter", '?');
            Log.i("MyReceiver", "Получен символ: " + c);
            if (randomCharEditText != null) {
                randomCharEditText.setText(String.valueOf(c));
            } else {
                Log.e("MyReceiver", "EditText is null");
            }
        }
    }
}
