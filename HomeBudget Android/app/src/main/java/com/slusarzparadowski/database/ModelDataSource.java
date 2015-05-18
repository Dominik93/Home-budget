package com.slusarzparadowski.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.slusarzparadowski.model.Category;
import com.slusarzparadowski.model.Element;
import com.slusarzparadowski.model.Model;
import com.slusarzparadowski.model.Settings;
import com.slusarzparadowski.model.User;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Dominik on 2015-05-08.
 */
public abstract class ModelDataSource {

    protected String[] allColumnsUser = { SQLite.COLUMN_ID,
            SQLite.COLUMN_NAME,
            SQLite.COLUMN_TOKEN,
            SQLite.COLUMN_SAVINGS};
    protected String[] allColumnsSettings = { SQLite.COLUMN_ID,
            SQLite.COLUMN_ID_USER,
            SQLite.COLUMN_AUTO_DELETE,
            SQLite.COLUMN_AUTO_SAVINGS,
            SQLite.COLUMN_AUTO_LOCAL_SAVE};
    protected String[] allColumnsCategory = { SQLite.COLUMN_ID,
            SQLite.COLUMN_ID_USER,
            SQLite.COLUMN_NAME,
            SQLite.COLUMN_TYPE };
    protected String[] allColumnsElement = { SQLite.COLUMN_ID,
            SQLite.COLUMN_ID_CATEGORY,
            SQLite.COLUMN_NAME,
            SQLite.COLUMN_VALUE,
            SQLite.COLUMN_CONST,
            SQLite.COLUMN_DATE };

    public abstract ArrayList<Element> getElements(long id_category);

    public abstract Element insertElement(Element element);

    public abstract void deleteElement(Element element);

    public abstract void updateElement(Element element);

    public abstract ArrayList<Category> getCategory(long id_user, String type);

    public abstract Category insertCategory(Category category);

    public abstract void deleteCategory(Category category);

    public abstract void updateCategory(Category category);

    public abstract Settings getSettings(long id_user);

    public abstract Settings insertSettings(Settings settings);

    public abstract void deleteSettings(Settings settings);

    public abstract void updateSettings(Settings settings);

    public abstract User getUser(String name, String token);

    public abstract String[] getUsers();

    public abstract User insertUser(User user);

    public abstract void deleteUser(User user);

    public abstract void updateUser(User user);

    public abstract Model getModel(String name, String token, Context context);

    public abstract Model insertModel(Model model);

    public abstract void deleteModel(Model model);

    public abstract void updateModel(Model model);

    public abstract void open() throws SQLException;

    public abstract void close();
}
