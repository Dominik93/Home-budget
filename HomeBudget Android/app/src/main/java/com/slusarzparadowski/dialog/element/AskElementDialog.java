package com.slusarzparadowski.dialog.element;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.slusarzparadowski.dialog.MyDialog;
import com.slusarzparadowski.homebudget.ElementActivity;
import com.slusarzparadowski.homebudget.MainActivity;
import com.slusarzparadowski.homebudget.R;
import com.slusarzparadowski.model.Model;

import java.sql.SQLException;

/**
 * Created by Dominik on 2015-04-05.
 */
public class AskElementDialog extends MyDialog {

    private Model model;
    private int indexCategory, indexElement;
    private String type;

    public AskElementDialog(Activity activity, int recourse, Model model, String type, int indexCategory, int indexElement) {
        super(activity, recourse);
        this.indexCategory = indexCategory;
        this.indexElement = indexElement;
        this.model = model;
        this.type = type;
    }

    public MyDialog buildDialog(){
        this.alertDialogBuilder = new AlertDialog.Builder(this.getActivity());

        this.alertDialogBuilder.setView(getView());

        this.alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Log.i(getClass().getSimpleName(), "Update");
                                dialog.cancel();
                                Intent intent = new Intent(activity, ElementActivity.class);
                                Bundle bundle = new Bundle();
                                bundle = ((MainActivity) activity).getModel().addToBundle(bundle);
                                bundle.putInt("category", indexCategory);
                                bundle.putInt("element", indexElement);
                                bundle.putString("type", type);
                                intent.putExtras(bundle);
                                activity.startActivityForResult(intent, 2);
                            }
                        })
                .setNeutralButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Log.i(getClass().getSimpleName(), "Delete C("+indexCategory+") E("+indexElement+")");
                                try {
                                    model.removeElementFromCategory(model.getMapList().get(type).get(indexCategory).getElementList().get(indexElement), indexCategory, type);
                                    Toast.makeText(activity, "Element deleted", Toast.LENGTH_LONG).show();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                //model.removeSpecialItem();
                                ((MainActivity)activity).getModel().notification();
                                dialog.cancel();
                            }
                        });
        this.createDialog();
        return this;
    }
}
