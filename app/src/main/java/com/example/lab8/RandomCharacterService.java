package com.example.lab8;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Random;

public class RandomCharacterService extends Service {

    private boolean isRunning = false;
    private final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isRunning = true;
        Toast.makeText(getApplicationContext(), "Service Started", Toast.LENGTH_SHORT).show();
        Log.i("RandomService", "Started");

        new Thread(() -> {
            while (isRunning) {
                try {
                    Thread.sleep(1000);
                    int index = new Random().nextInt(alphabet.length);
                    char letter = alphabet[index];

                    Intent broadcastIntent = new Intent("my.custom.action.tag.lab6");
                    broadcastIntent.putExtra("randomCharacter", letter);
                    sendBroadcast(broadcastIntent);

                    Log.i("RandomService", "Generated: " + letter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
        Toast.makeText(getApplicationContext(), "Service Stopped", Toast.LENGTH_SHORT).show();
        Log.i("RandomService", "Stopped");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
