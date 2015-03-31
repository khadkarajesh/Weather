package com.ekbana.weather.weather.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.ekbana.weather.weather.database.DatabaseHelper;

/**
 * Created by root on 2/22/15.
 */
public class WeatherDialog extends DialogFragment {

    Cursor cursor;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        cursor=new DatabaseHelper(getActivity()).getAllLocationData();
        builder.setMultiChoiceItems(cursor,"status","location",new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                Toast.makeText(getActivity(), "clicked position" + which, Toast.LENGTH_LONG).show();
                if(isChecked)
                {
                    new DatabaseHelper(getActivity()).updateLocationStatus(which+1,1);
                    cursor.requery();

                }
                else if(!isChecked)
                {
                    new DatabaseHelper(getActivity()).updateLocationStatus(which+1,0);
                    cursor.requery();
                }
            }
        });



        builder.setTitle("Choose location");
        Dialog dialog=builder.create();
        return dialog;
    }
}
