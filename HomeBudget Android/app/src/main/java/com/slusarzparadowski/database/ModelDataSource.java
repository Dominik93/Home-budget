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
public class ModelDataSource {

    private SQLiteDatabase database;
    private SQLite dbHelper;
    private String[] allColumnsUser = { SQLite.COLUMN_ID,
                                        SQLite.COLUMN_NAME,
                                        SQLite.COLUMN_TOKEN,
                                        SQLite.COLUMN_SAVINGS};
    private String[] allColumnsSettings = { SQLite.COLUMN_ID,
                                            SQLite.COLUMN_ID_USER,
                                            SQLite.COLUMN_AUTO_DELETE,
                                            SQLite.COLUMN_AUTO_SAVINGS};
    private String[] allColumnsCategory = { SQLite.COLUMN_ID,
                                            SQLite.COLUMN_ID_USER,
                                            SQLite.COLUMN_NAME,
                                            SQLite.COLUMN_TYPE };
    private String[] allColumnsElement = { SQLite.COLUMN_ID,
                                            SQLite.COLUMN_ID_CATEGORY,
                                            SQLite.COLUMN_NAME,
                                            SQLite.COLUMN_VALUE,
                                            SQLite.COLUMN_CONST,
                                            SQLite.COLUMN_DATE };


    public ModelDataSource(Context context) {
        dbHelper = new SQLite(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Element> getElements(long id_category){
        ArrayList<Element> elements = new ArrayList<>();

        Cursor cursor = database.query(SQLite.TABLE_ELEMENT, allColumnsElement, SQLite.COLUMN_ID_CATEGORY+"="+id_category, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Element element = new Element(cursor);
            elements.add(element);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return elements;
    }

    public Element insertElement(Element element){
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_NAME, element.getName());
        values.put(SQLite.COLUMN_VALUE, element.getValue());
        values.put(SQLite.COLUMN_CONST, element.isConstant());
        values.put(SQLite.COLUMN_DATE, element.getDate());
        long insertId = database.insert(SQLite.TABLE_ELEMENT, null,
                values);
        Cursor cursor = database.query(SQLite.TABLE_ELEMENT,
                allColumnsElement, SQLite.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        element = new Element(cursor);
        cursor.close();
        return element;
    }

    public void deleteElement(Element element){
        long id = element.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(SQLite.TABLE_ELEMENT, SQLite.COLUMN_ID + " = " + id, null);
    }

    public void updateElement(Element element){

    }

    public ArrayList<Category> getCategory(long id_user, String type){
        ArrayList<Category> categories = new ArrayList<>();

        Cursor cursor = database.query(SQLite.TABLE_CATEGORY, allColumnsCategory, SQLite.COLUMN_ID_USER+"="+ id_user+" and "+SQLite.COLUMN_TYPE+" like "+type, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Category category = new Category(cursor);
            category.setElementList(getElements(category.getId()));
            categories.add(category);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return categories;
    }

    public Category insertCategory(Category category){
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_ID_USER, category.getIdUser());
        values.put(SQLite.COLUMN_NAME, category.getName());
        values.put(SQLite.COLUMN_TYPE, category.getType());
        long insertId = database.insert(SQLite.TABLE_CATEGORY, null,
                values);
        Cursor cursor = database.query(SQLite.TABLE_CATEGORY,
                allColumnsElement, SQLite.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        category = new Category(cursor);
        cursor.close();
        return category;
    }

    public void deleteCategory(Category category){

    }

    public void updateCategory(Category category){

    }

    public Settings getSettings(long id_user){
        Cursor cursor = database.query(SQLite.TABLE_SETTINGS, allColumnsSettings, SQLite.COLUMN_ID_USER+"="+ id_user, null, null, null, null);
        cursor.moveToFirst();
        Settings settings = new Settings(cursor);
        cursor.close();
        return settings;
    }

    public Settings insertSettings(Settings settings){
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_ID_USER, settings.getIdUser());
        values.put(SQLite.COLUMN_AUTO_DELETE, settings.isAutoDeleting());
        values.put(SQLite.COLUMN_AUTO_SAVINGS, settings.isAutoSaving());
        long insertId = database.insert(SQLite.TABLE_SETTINGS, null,
                values);
        Cursor cursor = database.query(SQLite.TABLE_SETTINGS,
                allColumnsElement, SQLite.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        settings = new Settings(cursor);
        cursor.close();
        return settings;
    }

    public void deleteSettings(Settings settings){

    }

    public void updateSettings(Settings settings){

    }

    public User getUser(String token){
        Cursor cursor = database.query(SQLite.TABLE_USER, allColumnsUser, SQLite.COLUMN_TOKEN+" like "+ token, null, null, null, null);
        cursor.moveToFirst();
        User user = new User(cursor);
        user.setSettings(getSettings(user.getId()));
        cursor.close();
        return user;
    }

    public String[] getUsers(){
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
            temp[i] = users.get(i).getName();
        temp[i] = "Add user";
        cursor.close();
        return temp;

    }

    public User insertUser(User user){
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_NAME, user.getName());
        values.put(SQLite.COLUMN_TOKEN, user.getToken());
        values.put(SQLite.COLUMN_SAVINGS, user.getSavings());
        long insertId = database.insert(SQLite.TABLE_USER, null,
                values);
        Cursor cursor = database.query(SQLite.TABLE_USER,
                allColumnsElement, SQLite.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        user = new User(cursor);
        cursor.close();
        return user;
    }

    public void deleteUser(User user){

    }

    public void updateUser(User user){

    }

    public Model getModel(String token){
        Model model = new Model(false);
        model.setUser(getUser(token));
        model.setIncome(getCategory(model.getUser().getId(), "INCOME"));
        model.setOutcome(getCategory(model.getUser().getId(), "OUTCOME"));
        return model;
    }

    public void insertModel(Model model){
        model.setUser(this.insertUser(model.getUser()));
        model.getUser().setSettings(this.insertSettings(model.getUser().getSettings()));
        for (Category c : model.getOutcome())
            c = insertCategory(c);
        for (Category c : model.getOutcome())
            c = insertCategory(c);
    }

    public void deleteModel(Model model){

    }

    public void updateModel(Model model){

    }

}