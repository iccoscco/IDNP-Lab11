package com.example.labo_11;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// Case 1: Detecting network changes
public class Case1Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case1);

        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(new NetworkChangeReceiver(), filter);
    }

    public static class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Network connectivity changed", Toast.LENGTH_SHORT).show();
            context.startService(new Intent(context, NetworkService.class));
        }
    }

    public static class NetworkService extends Service {
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.d("NetworkService", "Syncing data with server...");
            stopSelf(); // Stop the service after task is done
            return START_NOT_STICKY;
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
