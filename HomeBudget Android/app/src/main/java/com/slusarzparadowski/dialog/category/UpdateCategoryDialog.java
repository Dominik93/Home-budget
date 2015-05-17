package com.slusarzparadowski.dialog.category;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;

import com.slusarzparadowski.dialog.MyDialog;
import com.slusarzparadowski.homebudget.MainActivity;
import com.slusarzparadowski.homebudget.R;
import com.slusarzparadowski.model.Category;
import com.slusarzparadowski.model.Model;

/**
 * Created by Dominik on 2015-04-13.
 */
public class UpdateCategoryDialog extends CategoryDialog {

    private int index;
    private EditText et;

    public UpdateCategoryDialog(Activity activity, int recourse, Model model, String type, int index) {
        super(activity, recourse, model, type);
        this.index = index;
        this.et = (EditText)this.view.findViewById(R.id.editTextPromptCategoryName);
        this.et.setText(model.getMapList().get(type).get(index).getName());
    }

    @Override
    public MyDialog buildDialog(){
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i(getClass().getSimpleName(), "OK");

                                if (!et.getText().toString().trim().equals("")) {

                                    model.updateCategory(new Category(model.getMapList().get(type).get(index).getId(),
                                                                        model.getMapList().get(type).get(index).getIdParent(),
                                                                        et.getText().toString(),
                                                                        model.getMapList().get(type).get(index).getType()),
                                                                        type, index);
                                    ((MainActivity) activity).getModel().notification();
                                    Log.i(getClass().getSimpleName(), "update Category(" + index + ")(" + et.getText().toString() + ")");
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
