package com.example.labo_11;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

// Case 3: Periodic alarms
public class Case3Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case3);

        // Obtener el servicio AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Crear una Intent explícita para el BroadcastReceiver
        Intent intent = new Intent(this, AlarmReceiver.class);

        // Crear el PendingIntent con FLAG_IMMUTABLE
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Intervalo de 1 minuto
        long interval = 60000;

        // Configurar la alarma repetitiva
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),
                interval,
                pendingIntent
        );
    }

    // BroadcastReceiver que será invocado por la alarma
    public static class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Iniciar el servicio cuando se reciba la alarma
            context.startService(new Intent(context, AlarmService.class));
        }
    }

    // Servicio que ejecuta la tarea periódica
    public static class AlarmService extends Service {
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.d("AlarmService", "Ejecutando tarea periódica...");
            // Finalizar el servicio después de la ejecución
            stopSelf();
            return START_NOT_STICKY;
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}

