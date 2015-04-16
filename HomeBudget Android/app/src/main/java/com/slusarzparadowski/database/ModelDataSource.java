package com.slusarzparadowski.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.slusarzparadowski.model.Category;
import com.slusarzparadowski.model.Element;
import com.slusarzparadowski.model.Model;
import com.slusarzparadowski.model.Settings;
import com.slusarzparadowski.model.User;

import java.sql.SQLException;

/**
 * Created by Dominik on 2015-04-17.
 */
public class ModelDataSource {

    private SQLiteDatabase database;
    private SQLite dbHelper;
    private String[] allColumnsUser = { SQLite.ID,
                                        SQLite.TOKEN,
                                        SQLite.SAVINGS};
    private String[] allColumnsSettings = { SQLite.ID,
                                            SQLite.ID_USER,
                                            SQLite.AUTO_DELETE,
                                            SQLite.AUTO_SAVINGS};
    private String[] allColumnsCategory = { SQLite.ID,
                                            SQLite.ID_USER,
                                            SQLite.NAME,
                                            SQLite.TYPE };
    private String[] allColumnsElement = { SQLite.ID,
                                            SQLite.ID_CATEGORY,
                                            SQLite.NAME,
                                            SQLite.VALUE,
                                            SQLite.CONST,
                                            SQLite.DATE };


    public ModelDataSource(Context context) {
        dbHelper = new SQLite(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertElement(Element element){

    }

    public void deleteElement(Element element){

    }

    public void updateElement(Element element){

    }

    public void insertCategory(Category category){

    }

    public void deleteCategory(Category category){

    }

    public void updateCategory(Category category){

    }

    public void insertSettings(Settings settings){

    }

    public void deleteSettings(Settings settings){

    }

    public void updateSettings(Settings settings){

    }

    public void insertUser(User user){

    }

    public void deleteUser(User user){

    }

    public void updateUser(User user){

    }

    public void insertModel(Model model){

    }

    public void deleteModel(Model model){

    }

    public void updateModel(Model model){

    }

}
