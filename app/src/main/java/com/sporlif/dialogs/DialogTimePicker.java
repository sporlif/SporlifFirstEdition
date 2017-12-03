package com.sporlif.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.sporlif.activities.user.ActRegist;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DialogTimePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    public static final String TAG_DATE_PICKER = "TAG_DATE_PICKER";
    public static final String TAG_TIME_PICKER = "TAG_TIME_PICKER";

    @Override
    public Dialog onCreateDialog(Bundle saved) {

        final GregorianCalendar c = (GregorianCalendar) Calendar.getInstance();
        String dialogType = this.getTag();

        switch (dialogType){

            case TAG_DATE_PICKER:
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(getActivity(), this, year, month, day);

            case TAG_TIME_PICKER:
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                return new TimePickerDialog(getActivity(), this, hour, minute,
                        DateFormat.is24HourFormat(getActivity()));

            default:
                Log.e("Error","Tipo de picker no reconocido");
                return null;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        GregorianCalendar gc = new GregorianCalendar();
        gc.set(Calendar.YEAR, year);
        gc.set(Calendar.MONTH, month);
        gc.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        if(getActivity() instanceof ActRegist){

        }

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

}
