package com.slusarzparadowski.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

/**
 * Created by Dominik on 2015-04-05.
 */
public class ElementDialog extends MyDialog {

    public ElementDialog(Activity activity, View view) {
        super(activity, view);
    }

    public MyDialog buildDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getActivity());

        alertDialogBuilder.setView(getView());

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Log.i(getClass().getSimpleName(), "Update");
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Log.i(getClass().getSimpleName(), "Delete");
                                dialog.cancel();
                            }
                        });
        
        this.setAlertDialog(alertDialogBuilder.create());
        return this;
    }

    @Override
    public void show( ) {
        this.getAlertDialog().show();
    }
}
