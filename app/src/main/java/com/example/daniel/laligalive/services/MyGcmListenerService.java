package com.example.daniel.laligalive.services;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.daniel.laligalive.R;
import com.example.daniel.laligalive.Utils;
import com.google.android.gms.gcm.GcmListenerService;
import com.google.android.gms.iid.InstanceIDListenerService;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    private String[] teams = {
            "Athletic",
            "Atlético",
            "Barcelona",
            "Betis",
            "Celta",
            "Deportivo",
            "Éibar",
            "Espanyol",
            "Getafe",
            "Granada",
            "Las Palmas",
            "Levante",
            "Málaga",
            "Rayo",
            "R. Madrid",
            "R. Sociedad",
            "Sevilla",
            "Sporting",
            "Valéncia",
            "Villareal"

    };
    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "Fromkk: " + from);
        Log.d(TAG, "Messagekk: " + message);


        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {

            // normal downstream message.
        }
        for (int i = 0; i < teams.length; ++i) {
            String equip = Utils.removeAccents(teams[i]);
            Log.v("MyGcmListenerService", equip);
            if (message.contains(equip)) {
                SharedPreferences sp2 = getBaseContext().getSharedPreferences(teams[i], Context.MODE_PRIVATE);
                if (sp2.getBoolean(teams[i], false)) {
                    sendNotificationLaliga(message);
                    break;
                }
            }
        }
    }

    private void sendNotificationLaliga(String message) {

        int icon = R.drawable.ic_launcher;
        if(message.contains("Barcelona")) {
            icon = R.drawable.bar;
        }else if(message.contains("R. Madrid")) {
            icon = R.drawable.rea;
        }else if(message.contains("Athletic")) {
            icon = R.drawable.ath;
        }else if(message.contains("Atletico")) {
            icon = R.drawable.atl;
        }else if(message.contains("Betis")) {
            icon = R.drawable.bet;
        }else if(message.contains("Celta")) {
            icon = R.drawable.cel;
        }else if(message.contains("Deportivo")) {
            icon = R.drawable.dep;
        }else if(message.contains("Eibar")) {
            icon = R.drawable.eib;
        }else if(message.contains("Espanyol")) {
            icon = R.drawable.esp;
        }else if(message.contains("Getafe")) {
            icon = R.drawable.get;
        }else if(message.contains("Granada")) {
            icon = R.drawable.gra;
        }else if(message.contains("Las Palmas")) {
            icon = R.drawable.las;
        }else if(message.contains("Levante")) {
            icon = R.drawable.lev;
        }else if(message.contains("Malaga")) {
            icon = R.drawable.mal;
        }else if(message.contains("Rayo")) {
            icon = R.drawable.ray;
        }else if(message.contains("Sociedad")) {
            icon = R.drawable.rso;
        }else if(message.contains("Sevilla")) {
            icon = R.drawable.sev;
        }else if(message.contains("Sporting")) {
            icon = R.drawable.spo;
        }else if(message.contains("Valencia")) {
            icon = R.drawable.val;
        }else if(message.contains("Villareal")) {
            icon = R.drawable.vil;
        }
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(icon)
                .setContentTitle("LaLigaLive")
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    // [END receive_message]

    public static class MyInstanceIDListenerService extends InstanceIDListenerService {

        private static final String TAG = "MyInstanceIDLS";

        /**
         * Called if InstanceID token is updated. This may occur if the security of
         * the previous token had been compromised. This call is initiated by the
         * InstanceID provider.
         */
        // [START refresh_token]
        @Override
        public void onTokenRefresh() {
            // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
        // [END refresh_token]
    }
}