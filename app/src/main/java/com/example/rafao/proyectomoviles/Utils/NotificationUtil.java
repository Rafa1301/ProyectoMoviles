package com.example.rafao.proyectomoviles.Utils;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationUtil extends FirebaseMessagingService {

    private static final String LOGTAG = "android-fcm";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage == null || remoteMessage.getNotification () == null) return;

        String titulo = remoteMessage.getNotification().getTitle();
        String texto = remoteMessage.getNotification().getBody();

        Log.d(LOGTAG, "NOTIFICACION RECIBIDA");
        Log.d(LOGTAG, "Título: " + titulo);
        Log.d(LOGTAG, "Texto: " + texto);

        sendNotification (remoteMessage.getNotification ().getTitle (), remoteMessage.getNotification ().getBody ());
    }

    private void sendNotification(String title, String body) {
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setContentTitle(title)
                        .setContentText(body);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
