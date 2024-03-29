package com.jayasundara.aifarm;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText date;

    public DateDialog(){}

    @SuppressLint("ValidFragment")
    public DateDialog(View view){

        date = (EditText) view;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this,year,month,day);
    }

    public void onDateSet(DatePicker view,int year,int month,int day){

        String date1 = day+"-"+(month+1)+"-"+year;
        date.setText(date1);
    }
}
