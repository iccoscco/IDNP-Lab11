package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar botones de control
        findViewById(R.id.btnStart).setOnClickListener(v -> sendBroadcastToReceiver("start"));
        findViewById(R.id.btnPause).setOnClickListener(v -> sendBroadcastToReceiver("pause"));
        findViewById(R.id.btnResume).setOnClickListener(v -> sendBroadcastToReceiver("resume"));
        findViewById(R.id.btnStop).setOnClickListener(v -> sendBroadcastToReceiver("stop"));
    }

    private void sendBroadcastToReceiver(String action) {
        Intent broadcastIntent = new Intent(this, AudioPlayerReceiver.class);
        broadcastIntent.setAction(action);
        sendBroadcast(broadcastIntent);
    }
}
