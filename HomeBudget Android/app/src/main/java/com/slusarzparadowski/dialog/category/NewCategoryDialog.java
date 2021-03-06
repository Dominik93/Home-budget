package com.slusarzparadowski.dialog.category;

import android.app.Activity;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.slusarzparadowski.dialog.MyDialog;
import com.slusarzparadowski.homebudget.MainActivity;
import com.slusarzparadowski.homebudget.R;
import com.slusarzparadowski.model.Category;
import com.slusarzparadowski.model.Model;

import java.sql.SQLException;

/**
 * Created by Dominik on 2015-04-13.
 */
public class NewCategoryDialog extends CategoryDialog {

    private EditText editText;

    public NewCategoryDialog(Activity activity, int recourse, Model model, String type) {
        super(activity, recourse, model, type);
        this.editText = (EditText)this.view.findViewById(R.id.editTextPromptCategoryName);
    }

    @Override
    public MyDialog buildDialog(){
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i(getClass().getSimpleName(), "OK");
                                if (!editText.getText().toString().trim().equals("")) {
                                    try {
                                        model.addCategory(new Category(0, ((MainActivity) activity).getModel().getUser().getId(), editText.getText().toString(), type), type);
                                        Toast.makeText(activity, "Category added", Toast.LENGTH_LONG).show();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                   // model.removeSpecialItem();
                                    ((MainActivity) activity).getModel().notification();
                                } else {
                                    Toast.makeText(activity, activity.getString(R.string.toast_category), Toast.LENGTH_LONG).show();
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
