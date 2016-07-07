package com.example.moham.contacting;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by moham on 6/14/2016.
 */
public class RingTonService extends Service {
    DBController controller;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ContacterModel name = (ContacterModel) intent.getSerializableExtra("name");
        Log.e("dddd", name.getName());

        NotificationManager notification_manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent Go_to_Mainactivity_intent = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pending_intent = PendingIntent.getActivity(this, 0, Go_to_Mainactivity_intent, 0);
        Notification notification = new Notification.Builder(this)
                .setContentTitle(name.getName())
                .setContentText("notification with " + name.getName())
                .setContentIntent(pending_intent)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.search)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification_manager.notify(0, notification);
        controller = new DBController(this.getApplicationContext());
        controller.update_willNotifyBoolean(name, name.getId());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

    }
}
