package com.example.moham.contacting;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class DeleteAlarm_class {
    Context context;
    int requestCode;

    public DeleteAlarm_class(Context context) {
        this.context = context;

    }

    public void delete_alarm( int Request_code) {
        this.requestCode = Request_code;
        android.app.AlarmManager alarm_manager = (android.app.AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        alarm_manager.cancel(sender);
    }

}
