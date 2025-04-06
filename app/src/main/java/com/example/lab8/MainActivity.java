package com.example.lab08;

import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lab8.R;

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

        startBtn.setOnClickListener(v -> startService(backgroundServiceIntent));
        stopBtn.setOnClickListener(v -> {
            stopService(backgroundServiceIntent);
            randomCharEditText.setText("");
        });
        nextBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, MainActivity2.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter("my.custom.action.tag.lab6");
        broadcastReceiver = new MyReceiver();
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            char c = intent.getCharExtra("randomCharacter", '?');
            randomCharEditText.setText(String.valueOf(c));
        }
    }
}
