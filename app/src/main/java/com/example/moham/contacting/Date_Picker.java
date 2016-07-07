package com.example.moham.contacting;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by moham on 6/18/2016.
 */
public class Date_Picker extends android.support.v4.app.DialogFragment implements DatePickerDialog.OnDateSetListener {
    public static DateModel dateModel=new DateModel();
    public Date_Picker() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        EditText viewById = (EditText) getActivity().findViewById(R.id.remind_edittext_id);
        dateModel.setYear(String.valueOf(year));
        dateModel.setMonth(String.valueOf(monthOfYear+1));
        dateModel.setDay(String.valueOf(dayOfMonth));
        viewById.setText(dateModel.toString());
    }
}
