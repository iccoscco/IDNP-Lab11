package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class AudioPlayerService extends Service {

    private MediaPlayer mediaPlayer;
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "audio_player_channel";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("AudioPlayerService", "Servicio creado");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent != null ? intent.getAction() : null;
        if (action != null) {
            switch (action) {
                case "start":
                    startAudio();
                    break;
                case "pause":
                    pauseAudio();
                    break;
                case "resume":
                    resumeAudio();
                    break;
                case "stop":
                    stopAudio();
                    break;
            }
        }
        return START_STICKY;
    }

    // Método para iniciar la reproducción
    private void startAudio() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }

            // Cargar el archivo desde res/raw/audio.mp3
            mediaPlayer = MediaPlayer.create(this, R.raw.audio);

            if (mediaPlayer != null) {
                mediaPlayer.start();
                showNotification();
                Log.d("AudioPlayerService", "Audio iniciado");
            } else {
                Log.e("AudioPlayerService", "No se pudo inicializar el MediaPlayer");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para pausar la reproducción
    private void pauseAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            showNotification();
            Log.d("AudioPlayerService", "Audio pausado");
        }
    }

    // Método para reanudar la reproducción
    private void resumeAudio() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            showNotification();
            Log.d("AudioPlayerService", "Audio reanudado");
        }
    }

    // Método para detener la reproducción
    private void stopAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            stopForeground(true);
            stopSelf();
            Log.d("AudioPlayerService", "Audio detenido y servicio finalizado");
        }
    }

    // Mostrar notificación en segundo plano
    @SuppressLint("ForegroundServiceType")
    private void showNotification() {
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Audio Player",
                    NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Reproduciendo audio")
                .setContentText("Controla la reproducción")
                .setSmallIcon(R.drawable.ic_play)
                .addAction(R.drawable.ic_pause, "Pausar", getActionPendingIntent("pause"))
                .addAction(R.drawable.ic_stop, "Detener", getActionPendingIntent("stop"))
                .build();

        startForeground(NOTIFICATION_ID, notification);
    }

    // Creación un PendingIntent para las acciones de la notificación
    private PendingIntent getActionPendingIntent(String action) {
        Intent intent = new Intent(this, AudioPlayerService.class);
        intent.setAction(action);
        return PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
