package com.slusarzparadowski.dialog.category;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.slusarzparadowski.dialog.MyDialog;
import com.slusarzparadowski.homebudget.MainActivity;
import com.slusarzparadowski.homebudget.R;
import com.slusarzparadowski.model.Category;
import com.slusarzparadowski.model.Element;

import java.util.ArrayList;

/**
 * Created by Dominik on 2015-04-13.
 */
public class NewCategoryDialog extends CategoryDialog {

    String type;
    EditText et;

    public NewCategoryDialog(Activity activity, int recourse, ArrayList<Category> list, String type) {
        super(activity, recourse, list);
        this.type = type;
        this.et = (EditText)this.view.findViewById(R.id.editTextPromptCategoryName);
    }

    @Override
    public MyDialog buildDialog(){
        alertDialogBuilder = new AlertDialog.Builder(this.getActivity());
        alertDialogBuilder.setView(getView());
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i(getClass().getSimpleName(), "OK");
                                if (!et.getText().toString().trim().equals("")) {
                                    if (list.contains(new Category(-1, activity.getApplicationContext().getString(R.string.add_category), "ADD")))
                                        list.remove(new Category(-1, activity.getApplicationContext().getString(R.string.add_category), "ADD"));

                                    for(Category c : list){
                                        if (c.getElementList().contains(new Element(-1, activity.getApplicationContext().getString(R.string.add_element))))
                                            c.getElementList().remove(new Element(-1, activity.getApplicationContext().getString(R.string.add_element)));
                                    }
                                    list.add(new Category(0, et.getText().toString().trim(), type));
                                    ((MainActivity)activity).getModel().notification();

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
        this.createDialog();
        return this;
    }

}
