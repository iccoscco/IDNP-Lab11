package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AudioPlayerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                Intent serviceIntent = new Intent(context, AudioPlayerService.class);
                serviceIntent.setAction(action);
                context.startService(serviceIntent);
            }
        }
    }
}
