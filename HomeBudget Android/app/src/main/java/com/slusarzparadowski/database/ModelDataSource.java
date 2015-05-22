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

    public abstract ArrayList<Element> getElements(long id_category) throws SQLException;

    public abstract Element insertElement(Element element) throws SQLException;

    public abstract void deleteElement(Element element) throws SQLException;

    public abstract void updateElement(Element element) throws SQLException;

    public abstract ArrayList<Category> getCategories(long id_user, String type) throws SQLException;

    public abstract Category insertCategory(Category category) throws SQLException;

    public abstract void deleteCategory(Category category) throws SQLException;

    public abstract void updateCategory(Category category) throws SQLException;

    public abstract Settings getSettings(long id_user) throws SQLException;

    public abstract Settings insertSettings(Settings settings) throws SQLException;

    public abstract void deleteSettings(Settings settings) throws SQLException;

    public abstract void updateSettings(Settings settings) throws SQLException;

    public abstract User getUser(String name, String token) throws SQLException;

    public abstract String[] getUsers() throws SQLException;

    public abstract User insertUser(User user) throws SQLException;

    public abstract void deleteUser(User user) throws SQLException;

    public abstract void updateUser(User user) throws SQLException;

    public abstract Model getModel(String name, String token, Context context) throws SQLException;

    public abstract Model insertModel(Model model) throws SQLException;

    public abstract void deleteModel(Model model) throws SQLException;

    public abstract void updateModel(Model model) throws SQLException;

    public abstract void open() throws SQLException;

    public abstract void close();
}
