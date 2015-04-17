package com.slusarzparadowski.dialog.category;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;

import com.slusarzparadowski.database.ModelDataSource;
import com.slusarzparadowski.dialog.MyDialog;
import com.slusarzparadowski.homebudget.MainActivity;
import com.slusarzparadowski.homebudget.R;
import com.slusarzparadowski.model.Category;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Dominik on 2015-04-09.
 */
public class AskCategoryDialog extends CategoryDialog{

    int index;

    public AskCategoryDialog(Activity activity, int recourse, ArrayList<Category> list, ModelDataSource modelDataSource, int index) {
        super(activity, recourse, list, modelDataSource);
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
                                new UpdateCategoryDialog(activity, R.layout.prompts_category, list, modelDataSource, index).buildDialog().show();
                                //TODO: new dialog with update current category
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
                                    modelDataSource.open();
                                } catch (SQLException e) {
                                    Log.i(getClass().getSimpleName(), e.toString());
                                }
                                modelDataSource.deleteCategory(list.get(index));
                                modelDataSource.close();
                                list.remove(index);
                                ((MainActivity)getActivity()).getModel().notification();
                                dialog.cancel();
                            }
                        });

        this.createDialog();
        return this;
    }

}
