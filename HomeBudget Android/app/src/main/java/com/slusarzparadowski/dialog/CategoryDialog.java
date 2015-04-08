package com.slusarzparadowski.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.slusarzparadowski.homebudget.R;
import com.slusarzparadowski.model.Category;
import com.slusarzparadowski.model.Element;

import java.util.ArrayList;

/**
 * Created by Dominik on 2015-04-04.
 */
public class CategoryDialog extends MyDialog{

    ArrayList<Category> list;
    String type;
    EditText et;

    public CategoryDialog(Activity activity, View view, ArrayList<Category> list, String type) {
        super(activity, view);
        this.list = list;
        this.type = type;
        this.et = (EditText)this.view.findViewById(R.id.editTextPromptCategoryName);
    }

    public MyDialog buildDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        alertDialogBuilder.setView(getView());

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i(getClass().getSimpleName(), "OK");
                                if (!et.getText().toString().trim().equals("")) {
                                    if (!list.contains(new Category(-1, activity.getApplicationContext().getString(R.string.add_category), "ADD")))
                                        list.add(new Category(-1, activity.getApplicationContext().getString(R.string.add_category), "ADD"));

                                    for(Category c : list){
                                        if (!c.getElementList().contains(new Element(-1, activity.getApplicationContext().getString(R.string.add_element))))
                                            c.getElementList().add(new Element(-1, activity.getApplicationContext().getString(R.string.add_element)));
                                    }
                                    list.add(new Category(0, et.getText().toString().trim(), type));
                                    Log.i(getClass().getSimpleName(), "new Category(0, " + et.getText().toString() + ", " + type + ")");
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
