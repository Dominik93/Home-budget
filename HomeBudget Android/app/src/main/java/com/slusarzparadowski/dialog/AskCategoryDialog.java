package com.slusarzparadowski.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.slusarzparadowski.homebudget.MainActivity;
import com.slusarzparadowski.homebudget.R;
import com.slusarzparadowski.model.Category;

import java.util.ArrayList;

/**
 * Created by Dominik on 2015-04-09.
 */
public class AskCategoryDialog extends MyDialog{

    ArrayList<Category> list;
    int index;

    public AskCategoryDialog(Activity activity, View view, ArrayList<Category> list, int index) {
        super(activity, view);
        this.list = list;
        this.index = index;
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
                                Log.i(getClass().getSimpleName(), "Delete C("+index+")");
                                list.remove(index);
                                ((MainActivity)getActivity()).getModel().notification();
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
