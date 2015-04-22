package com.slusarzparadowski.dialog.element;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.slusarzparadowski.database.ModelDataSource;
import com.slusarzparadowski.dialog.MyDialog;
import com.slusarzparadowski.homebudget.ElementActivity;
import com.slusarzparadowski.homebudget.MainActivity;
import com.slusarzparadowski.homebudget.R;
import com.slusarzparadowski.model.Category;
import com.slusarzparadowski.model.Element;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Dominik on 2015-04-05.
 */
public class AskElementDialog extends MyDialog {

    ArrayList<Category> list;
    int indexCategory, indexElement;
    String type;
    ModelDataSource modelDataSource;

    public AskElementDialog(Activity activity, int recourse, ArrayList<Category> list, ModelDataSource modelDataSource, String type, int indexCategory, int indexElement) {
        super(activity, recourse);
        this.indexCategory = indexCategory;
        this.indexElement = indexElement;
        this.list = list;
        this.type = type;
        this.modelDataSource = modelDataSource;
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
                                if (!list.contains(new Category(-1, -1, activity.getString(R.string.add_category), "ADD")))
                                    list.add(new Category(-1, -1, activity.getString(R.string.add_category), "ADD"));

                                for(Category c : list){
                                    if (!c.getElementList().contains(new Element(-1, -1, activity.getString(R.string.add_element))))
                                        c.getElementList().add(new Element(-1, -1, activity.getString(R.string.add_element)));
                                }
                                try {
                                    modelDataSource.open();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                modelDataSource.deleteElement(list.get(indexCategory).getElementList().get(indexElement));
                                modelDataSource.close();
                                list.get(indexCategory).getElementList().remove(indexElement);
                                ((MainActivity)activity).getModel().notification();
                                dialog.cancel();
                            }
                        });
        this.createDialog();
        return this;
    }

    @Override
    public void show( ) {
        this.getAlertDialog().show();
    }
}
