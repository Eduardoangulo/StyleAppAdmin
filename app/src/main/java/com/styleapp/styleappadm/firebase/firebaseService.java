package com.styleapp.styleappadm.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static com.styleapp.styleappadm.VariablesGlobales.TAG;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.styleapp.styleappadm.R;
import com.styleapp.styleappadm.MainActivity;

/**
 * Created by Luis on 04/07/2017.
 */

public class firebaseService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String from=remoteMessage.getFrom();
        Log.d(TAG, "Mensaje recibido de: "+from);
        if(remoteMessage.getNotification()!=null){
            Log.d(TAG, "Notificacion: "+remoteMessage.getNotification().getBody());
            mostrarNotificacion(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
        if(remoteMessage.getData().size()>0){
            Log.d(TAG, "Notificacion-data: "+remoteMessage.getData());
        }
    }

    private void mostrarNotificacion(String title, String body) {
        Intent intent= new Intent(this, MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificatiBuilder=new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.haircut_icon)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(soundUri)
                    .setContentIntent(pendingIntent);
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificatiBuilder.build());
    }
}
