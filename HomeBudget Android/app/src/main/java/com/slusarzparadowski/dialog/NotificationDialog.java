package com.slusarzparadowski.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.TextView;

import com.slusarzparadowski.homebudget.R;

/**
 * Created by Dominik on 2015-05-05.
 */
public class NotificationDialog extends MyDialog{

    public NotificationDialog(Activity activity, int recourse, String message) {
        super(activity, recourse);
        ((TextView)this.view.findViewById(R.id.textViewNotification)).setText(message);
    }

    public MyDialog buildDialog(){

        this.alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i(getClass().getSimpleName(), "OK");
                                dialog.cancel();
                            }
                        });

        this.createDialog();
        return this;
    }
}
