package com.slusarzparadowski.dialog.category;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.slusarzparadowski.dialog.MyDialog;
import com.slusarzparadowski.homebudget.MainActivity;
import com.slusarzparadowski.homebudget.R;
import com.slusarzparadowski.model.Category;
import com.slusarzparadowski.model.Element;

import java.util.ArrayList;

/**
 * Created by Dominik on 2015-04-04.
 */
public abstract class CategoryDialog extends MyDialog {

    protected ArrayList<Category> list;

    public CategoryDialog(Activity activity, int recourse, ArrayList<Category> list) {
        super(activity, recourse);
        this.list = list;
    }

}
