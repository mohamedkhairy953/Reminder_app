package com.example.moham.contacting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by moham on 6/14/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            ContacterModel name = (ContacterModel) intent.getSerializableExtra("name");
            Log.e("eeeeeeee", name.getName());
            Intent Go_to_service_intent = new Intent(context, RingTonService.class);
            Go_to_service_intent.putExtra("name", name);
            context.startService(Go_to_service_intent);
        } catch (Exception d) {

        }
    }

}
