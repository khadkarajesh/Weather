package com.ekbana.weather.weather.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.ekbana.weather.weather.database.DatabaseHelper;

/**
 * Created by root on 2/23/15.
 */
public class PinnedDialog extends DialogFragment {

    Cursor cursor;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        cursor = new DatabaseHelper(getActivity()).getAllPinnedData();
        builder.setMultiChoiceItems(cursor, "status", "username", new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                Toast.makeText(getActivity(), "clicked position" + which, Toast.LENGTH_LONG).show();
                if (isChecked) {
                    new DatabaseHelper(getActivity()).updatePinnedStatus(which + 1, 1);
                    cursor.requery();

                } else if (!isChecked) {
                    new DatabaseHelper(getActivity()).updatePinnedStatus(which + 1, 0);
                    cursor.requery();
                }
            }
        });


        builder.setTitle("Choose User");
        Dialog dialog = builder.create();
        return dialog;

    }
}
