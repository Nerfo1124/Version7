package com.example.usuario.version7.Import;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Created by Usuario on 21/09/2015.
 */
public class DateDialog extends DialogFragment implements  DatePickerDialog.OnDateSetListener{

    EditText txtDate;

    public DateDialog(View view){
        txtDate=(EditText)view;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), this, 1979, 12, 31);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = day+"-"+(month+1)+"-"+year;
        txtDate.setText(date);
    }
}
