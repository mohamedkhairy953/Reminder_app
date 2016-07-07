package com.example.moham.contacting;

import android.app.AlarmManager;
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

import java.io.Serializable;
import java.util.Calendar;

public class AlarmManager_Activity extends AppCompatActivity {

    AlarmManager alarm_manager;
    TimePicker alarm_timePicker;
    Button btn_turn_on;
    TextView set_alarm;
    Calendar calendar;
    PendingIntent pending_intent;
    DBController dbController;
    Intent intent;
    DateModel date;
    int request_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm_timePicker = (TimePicker) findViewById(R.id.timePicker);
        btn_turn_on = (Button) this.findViewById(R.id.turnon_btn);
        set_alarm = (TextView) findViewById(R.id.set_alarm_txt);
        calendar = Calendar.getInstance();
        intent = new Intent(AlarmManager_Activity.this, AlarmReceiver.class);
        date = (DateModel) getIntent().getExtras().getSerializable("date");
        request_code = getIntent().getIntExtra("request_code", 0);
        btn_turn_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm_manager(date, request_code);

            }
        });

    }

    public void setAlarm_manager(DateModel date, int Request_code) {

        calendar.set(Calendar.MONTH, Integer.parseInt(date.getMonth()) - 1);
        calendar.set(Calendar.YEAR, Integer.parseInt(date.getYear()));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.getDay()));
        calendar.set(Calendar.HOUR_OF_DAY, alarm_timePicker.getHour());
        calendar.set(Calendar.MINUTE, alarm_timePicker.getMinute());
        int hour = alarm_timePicker.getHour();
        int minut = alarm_timePicker.getMinute();
        dbController = new DBController(this);
        ContacterModel someone = dbController.get_someone(Request_code);
        intent.putExtra("name", someone);
        set_alarm.setText("set time to " + date.toString() + "/" + hour + ((minut >= 10) ? ":" : ":0") + minut);
        pending_intent = PendingIntent.getBroadcast(AlarmManager_Activity.this, Request_code, intent, 0);
        alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);
        someone.setWill_notify(true);
        dbController.update_willNotifyBoolean(someone, Request_code);
        finish();

    }


}
