package com.slusarzparadowski.dialog.category;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.slusarzparadowski.dialog.MyDialog;
import com.slusarzparadowski.homebudget.MainActivity;
import com.slusarzparadowski.homebudget.R;
import com.slusarzparadowski.model.Model;

import java.sql.SQLException;

/**
 * Created by Dominik on 2015-04-09.
 */
public class AskCategoryDialog extends CategoryDialog {

    private int index;

    public AskCategoryDialog(Activity activity, int recourse, Model model, String type, int index) {
        super(activity, recourse, model, type);
        this.index = index;
    }

    public MyDialog buildDialog(){
        this.alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                Log.i(getClass().getSimpleName(), "Update");
                                dialog.cancel();
                                new UpdateCategoryDialog(activity, R.layout.prompts_category, model, type, index).buildDialog().show();
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
                                Log.i(getClass().getSimpleName(), "Delete C("+index+")");
                                try {
                                    model.removeCategory(model.getMapList().get(type).get(index), type);
                                    Toast.makeText(activity, "Category deleted", Toast.LENGTH_LONG).show();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                //model.removeSpecialItem();
                                ((MainActivity)getActivity()).getModel().notification();
                                dialog.cancel();
                            }
                        });

        this.createDialog();
        return this;
    }

}
