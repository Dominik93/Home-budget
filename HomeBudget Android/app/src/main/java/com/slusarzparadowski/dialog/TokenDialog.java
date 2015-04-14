package com.slusarzparadowski.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.slusarzparadowski.homebudget.R;
import com.slusarzparadowski.model.User;

import java.util.ArrayList;

/**
 * Created by Dominik on 2015-04-06.
 */
public class TokenDialog extends MyDialog {

    EditText et;
    User user;

    public TokenDialog(Activity activity, int recourse, User user) {
        super(activity, recourse);
        this.et = (EditText)this.view.findViewById(R.id.editTextTokenDialog);
        this.user = user;
    }

    public MyDialog buildDialog(){
        this.alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i(getClass().getSimpleName(), "OK");
                                if (!et.getText().toString().trim().equals("")) {
                                    user.setToken(et.getText().toString().trim());
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i(getClass().getSimpleName(), "Cancel");
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
