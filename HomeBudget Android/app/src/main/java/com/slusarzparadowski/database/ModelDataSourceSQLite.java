package com.slusarzparadowski.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.slusarzparadowski.homebudget.R;
import com.slusarzparadowski.model.Category;
import com.slusarzparadowski.model.Element;
import com.slusarzparadowski.model.Model;
import com.slusarzparadowski.model.Settings;
import com.slusarzparadowski.model.User;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Dominik on 2015-04-17.
 */
public class ModelDataSourceSQLite extends ModelDataSource{

    private SQLiteDatabase database;
    private SQLite dbHelper;

    public ModelDataSourceSQLite(Context context) {
        dbHelper = new SQLite(context);
    }

    @Override
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        database.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Override
    public void close() {
        dbHelper.close();
    }

    @Override
    public ArrayList<Element> getElements(long id_category) throws SQLException {
        this.open();
        ArrayList<Element> elements = new ArrayList<>();
        Cursor cursor = database.query(SQLite.TABLE_ELEMENT, allColumnsElement, SQLite.COLUMN_ID_CATEGORY+"="+id_category, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Element element = new Element(cursor);
            elements.add(element);
            cursor.moveToNext();
        }
        cursor.close();
        this.close();
        return elements;
    }

    @Override
    public Element insertElement(Element element) throws SQLException {
        this.open();
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_NAME, element.getName());
        values.put(SQLite.COLUMN_ID_CATEGORY, element.getIdParent());
        values.put(SQLite.COLUMN_VALUE, element.getValue());
        values.put(SQLite.COLUMN_CONST, element.isConstant() ? 1 : 0);
        if (element.getDate() != null)
            values.put(SQLite.COLUMN_DATE, element.getDate().toString());
        long insertId = database.insert(SQLite.TABLE_ELEMENT, null,
                values);
        Cursor cursor = database.query(SQLite.TABLE_ELEMENT,
                allColumnsElement, SQLite.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        element = new Element(cursor);
        cursor.close();
        this.close();
        return element;
    }

    @Override
    public void deleteElement(Element element) throws SQLException {
        this.open();
        database.delete(SQLite.TABLE_ELEMENT, SQLite.COLUMN_ID + " = " + element.getId(), null);
        this.close();
    }

    @Override
    public void updateElement(Element element) throws SQLException {
        this.open();
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_NAME, element.getName());
        values.put(SQLite.COLUMN_VALUE, element.getValue());
        values.put(SQLite.COLUMN_CONST, element.isConstant() ? 1 : 0);
        values.put(SQLite.COLUMN_DATE, element.getDate().toString());
        database.update(SQLite.TABLE_ELEMENT, values, SQLite.COLUMN_ID+"="+element.getId(), null);
        this.close();
    }

    @Override
    public ArrayList<Category> getCategories(long id_user, String type) throws SQLException {
        this.open();
        ArrayList<Category> categories = new ArrayList<>();
        Cursor cursor = database.query(SQLite.TABLE_CATEGORY, allColumnsCategory, SQLite.COLUMN_ID_USER+"="+ id_user+" and "+SQLite.COLUMN_TYPE+" like '"+type+"'", null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Category category = new Category(cursor);
            category.setElementList(getElements(category.getId()));
            categories.add(category);
            cursor.moveToNext();
        }
        cursor.close();
        this.close();
        return categories;
    }

    @Override
    public Category insertCategory(Category category) throws SQLException {
        this.open();
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_ID_USER, category.getIdParent());
        values.put(SQLite.COLUMN_NAME, category.getName());
        values.put(SQLite.COLUMN_TYPE, category.getType());
        long insertId = database.insert(SQLite.TABLE_CATEGORY, null,
                values);
        Cursor cursor = database.query(SQLite.TABLE_CATEGORY,
                allColumnsCategory, SQLite.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Category categoryNew = new Category(cursor);
        categoryNew.setElementList(category.getElementList());
        for(int i =0; i < categoryNew.getElementList().size(); i++){
            categoryNew.getElementList().get(i).setIdParent(categoryNew.getId());
            categoryNew.getElementList().set(i, insertElement(categoryNew.getElementList().get(i)));
        }
        cursor.close();
        this.close();
        return categoryNew;
    }

    @Override
    public void deleteCategory(Category category) throws SQLException {
        this.open();
        database.delete(SQLite.TABLE_CATEGORY, SQLite.COLUMN_ID + " = " + category.getId(), null);
        this.close();
    }

    @Override
    public void updateCategory(Category category) throws SQLException {
        this.open();
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_NAME, category.getName());
        values.put(SQLite.COLUMN_TYPE, category.getType());
        database.update(SQLite.TABLE_CATEGORY, values, SQLite.COLUMN_ID+"="+category.getId(), null);
        this.close();
    }

    @Override
    public Settings getSettings(long id_user) throws SQLException {
        this.open();
        Cursor cursor = database.query(SQLite.TABLE_SETTINGS, allColumnsSettings, SQLite.COLUMN_ID_USER+"="+ id_user, null, null, null, null);
        cursor.moveToFirst();
        Settings settings = new Settings(cursor);
        cursor.close();
        this.close();
        return settings;
    }

    @Override
    public Settings insertSettings(Settings settings) throws SQLException {
        this.open();
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_ID_USER, settings.getIdParent());
        values.put(SQLite.COLUMN_AUTO_DELETE, settings.isAutoDeleting() ? 1 : 0 );
        values.put(SQLite.COLUMN_AUTO_SAVINGS, settings.isAutoSaving() ? 1 : 0);
        values.put(SQLite.COLUMN_AUTO_LOCAL_SAVE, settings.isAutoLocalSave() ? 1 : 0);
        long insertId = database.insert(SQLite.TABLE_SETTINGS, null,
                values);
        Cursor cursor = database.query(SQLite.TABLE_SETTINGS,
                allColumnsSettings, SQLite.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        settings = new Settings(cursor);
        cursor.close();
        this.close();
        return settings;
    }

    @Override
    public void deleteSettings(Settings settings) throws SQLException {
        this.open();
        database.delete(SQLite.TABLE_SETTINGS, SQLite.COLUMN_ID_USER + " like '" + settings.getIdParent()+"'", null);
        this.close();
    }

    @Override
    public void updateSettings(Settings settings) throws SQLException {
        this.open();
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_ID_USER, settings.getIdParent());
        values.put(SQLite.COLUMN_AUTO_DELETE, settings.isAutoDeleting() ? 1 : 0 );
        values.put(SQLite.COLUMN_AUTO_SAVINGS, settings.isAutoSaving() ? 1 : 0);
        values.put(SQLite.COLUMN_AUTO_LOCAL_SAVE, settings.isAutoLocalSave() ? 1 : 0);
        database.update(SQLite.TABLE_SETTINGS, values, SQLite.COLUMN_ID+"="+settings.getId(), null);
        this.close();
    }

    @Override
    public User getUser(String name, String token) throws SQLException {
        this.open();
        Cursor cursor = database.query(SQLite.TABLE_USER, allColumnsUser, SQLite.COLUMN_TOKEN+" like '"+ token +"' and " + SQLite.COLUMN_NAME +" like '"+ name+"'", null, null, null, null);
        cursor.moveToFirst();
        User user = new User(cursor);
        user.setSettings(getSettings(user.getId()));
        cursor.close();
        this.close();
        return user;
    }

    @Override
    public String[] getUsers() throws SQLException {
        this.open();
        ArrayList<User> users = new ArrayList<>();
        String [] temp;
        Cursor cursor = database.query(SQLite.TABLE_USER, allColumnsUser, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = new User(cursor);
            users.add(user);
            cursor.moveToNext();
        }
        temp = new String[users.size() + 1];
        int i;
        for(i = 0; i < users.size(); i++)
            temp[i] = users.get(i).getName() + "-" +users.get(i).getToken();
        temp[i] = "Add user";
        cursor.close();
        this.close();
        return temp;

    }

    @Override
    public User insertUser(User user) throws SQLException {
        this.open();
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_NAME, user.getName());
        values.put(SQLite.COLUMN_TOKEN, user.getToken());
        values.put(SQLite.COLUMN_SAVINGS, user.getSavings());
        long insertId = database.insert(SQLite.TABLE_USER, null, values);
        Cursor cursor = database.query(SQLite.TABLE_USER,
                allColumnsUser, SQLite.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        user = new User(cursor);
        user.getSettings().setIdParent(user.getId());
        cursor.close();
        this.close();
        return user;
    }

    @Override
    public void deleteUser(User user) throws SQLException {
        this.open();
        database.delete(SQLite.TABLE_USER, SQLite.COLUMN_NAME + " like '" + user.getName() + "' and "+ SQLite.COLUMN_TOKEN +" like '"+ user.getToken() +"'", null);
        this.close();
    }

    @Override
    public void updateUser(User user) throws SQLException {
        this.open();
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_NAME, user.getName());
        values.put(SQLite.COLUMN_TOKEN, user.getToken());
        values.put(SQLite.COLUMN_SAVINGS, user.getSavings());
        database.update(SQLite.TABLE_USER, values, SQLite.COLUMN_ID+"="+user.getId() ,null);
        this.close();
    }

    @Override
    public Model getModel(String name, String token, Context context) throws SQLException {
        this.open();
        Model model = new Model(false, context);
        model.setUser(getUser(name, token));
        model.setIncome(getCategories(model.getUser().getId(), "INCOME"));
        model.setOutcome(getCategories(model.getUser().getId(), "OUTCOME"));
        this.close();
        return model;
    }

    @Override
    public Model insertModel(Model model) throws SQLException {
        this.open();
        model.setUser(this.insertUser(model.getUser()));
        model.getUser().setSettings(this.insertSettings(model.getUser().getSettings()));
        for(int i = 0; i < model.getOutcome().size(); i++){
            model.getOutcome().set(i, this.insertCategory(model.getOutcome().get(i)));
        }
        for(int i = 0; i < model.getIncome().size(); i++){
            model.getIncome().set(i, this.insertCategory(model.getIncome().get(i)));
        }
        this.close();
        return model;
    }

    @Override
    public void deleteModel(Model model) throws SQLException {
        this.open();
        this.deleteUser(model.getUser());
        this.close();
    }

    @Override
    public void updateModel(Model model) throws SQLException {
        this.open();
        this.updateUser(model.getUser());
        this.updateSettings(model.getUser().getSettings());
        for(int i = 0; i < model.getOutcome().size(); i++){
            for(int j = 0; j < model.getOutcome().get(i).getElementList().size(); j++){
                this.updateElement(model.getOutcome().get(i).getElementList().get(j));
            }
            this.updateCategory(model.getOutcome().get(i));
        }
        for(int i = 0; i < model.getIncome().size(); i++){
            for(int j = 0; j < model.getIncome().get(i).getElementList().size(); j++){
                this.updateElement(model.getIncome().get(i).getElementList().get(j));
            }
            this.updateCategory(model.getIncome().get(i));
        }
        this.close();
    }

}
