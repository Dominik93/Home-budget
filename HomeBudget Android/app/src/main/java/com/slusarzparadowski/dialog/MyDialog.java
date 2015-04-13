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
    protected AlertDialog.Builder alertDialogBuilder;

    public MyDialog(Activity activity, int recourse){
        this.activity = activity;
        this.view = LayoutInflater.from(activity.getApplicationContext()).inflate(recourse, null);
        this.alertDialogBuilder = new AlertDialog.Builder(this.activity);
        this.alertDialogBuilder.setView(this.view);
    }

    public abstract MyDialog buildDialog();

    public void createDialog(){
        this.alertDialog = this.alertDialogBuilder.create();
    }

    public void show( ) {
        this.alertDialog.show();
    }

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
