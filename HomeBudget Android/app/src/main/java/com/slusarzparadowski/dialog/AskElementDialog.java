package com.slusarzparadowski.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

import com.slusarzparadowski.homebudget.MainActivity;
import com.slusarzparadowski.model.Category;
import com.slusarzparadowski.model.Element;

import java.util.ArrayList;

/**
 * Created by Dominik on 2015-04-05.
 */
public class AskElementDialog extends MyDialog {

    ArrayList<Category> list;
    int indexCategory, indexElement;

    public AskElementDialog(Activity activity, View view, ArrayList<Category> list, int indexCategory, int indexElement) {
        super(activity, view);
        this.indexCategory = indexCategory;
        this.indexElement = indexElement;
        this.list = list;
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
                                Log.i(getClass().getSimpleName(), "Delete C("+indexCategory+") E("+indexElement+")");
                                list.get(indexCategory).getElementList().remove(indexElement);
                                ((MainActivity)activity).getModel().notification();
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
