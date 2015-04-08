package com.slusarzparadowski.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.slusarzparadowski.homebudget.R;

/**
 * Created by Dominik on 2015-04-04.
 */
public abstract class MyDialog {

    protected Activity activity;
    protected View view;
    protected AlertDialog alertDialog;

    public MyDialog(Activity activity, View view){
        this.activity = activity;
        this.view = view;

    }

    public abstract MyDialog buildDialog();
    public abstract void show();


    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public void setAlertDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }
}
