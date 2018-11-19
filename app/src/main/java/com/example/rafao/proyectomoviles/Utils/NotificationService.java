package com.example.rafao.proyectomoviles.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.rafao.proyectomoviles.MainActivity;
import com.example.rafao.proyectomoviles.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class NotificationService extends FirebaseMessagingService {

    String TAG = "NotificationService: ";
    @Override
    public void onNewToken (String s) {
        super.onNewToken (s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage == null || remoteMessage.getNotification () == null) return;
        sendNotification (remoteMessage.getNotification ().getTitle (), remoteMessage.getNotification ().getBody ());
    }

    void sendNotification (String title, String body) {
        Intent intent = new Intent (this, MainActivity.class);
        intent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra ("body", body);

        PendingIntent pendingIntent = PendingIntent.getActivity (this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri uriSound = RingtoneManager.getDefaultUri (RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder (this, "NOTIFICATIONCHANNEL24");
        notificationBuilder.setSmallIcon (R.drawable.uv)
                .setContentTitle (title)
                .setContentText (body)
                .setAutoCancel (true)
                .setSound (uriSound)
                .setContentIntent (pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService (Context.NOTIFICATION_SERVICE);
        if (notificationManager != null)
            notificationManager.notify (0, notificationBuilder.build ());
    }
}
