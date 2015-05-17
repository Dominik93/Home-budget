package com.slusarzparadowski.dialog.category;

import android.app.Activity;

import com.slusarzparadowski.dialog.MyDialog;
import com.slusarzparadowski.model.Model;

/**
 * Created by Dominik on 2015-04-04.
 */
public abstract class CategoryDialog extends MyDialog {

    protected Model model;
    protected String type;
    public CategoryDialog(Activity activity, int recourse, Model model, String type) {
        super(activity, recourse);
        this.model = model;
        this.type = type;
    }

}
