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
        database.execSQL("PRAGMA foreign_keys = ON;");
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
        return element;
    }

    public void deleteElement(Element element){
        database.delete(SQLite.TABLE_ELEMENT, SQLite.COLUMN_ID + " = " + element.getId(), null);
    }

    public void updateElement(Element element){
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_NAME, element.getName());
        values.put(SQLite.COLUMN_VALUE, element.getValue());
        values.put(SQLite.COLUMN_CONST, element.isConstant() ? 1 : 0);
        values.put(SQLite.COLUMN_DATE, element.getDate().toString());
        database.update(SQLite.TABLE_ELEMENT, values, SQLite.COLUMN_ID+"="+element.getId() ,null);
    }

    public ArrayList<Category> getCategory(long id_user, String type){
        ArrayList<Category> categories = new ArrayList<>();

        Cursor cursor = database.query(SQLite.TABLE_CATEGORY, allColumnsCategory, SQLite.COLUMN_ID_USER+"="+ id_user+" and "+SQLite.COLUMN_TYPE+" like '"+type+"'", null, null, null, null);
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
        return categoryNew;
    }

    public void deleteCategory(Category category){
        /*for(int i = 0; i < category.getElementList().size(); i++){
            this.deleteElement(category.getElementList().get(i));
        }*/
        database.delete(SQLite.TABLE_CATEGORY, SQLite.COLUMN_ID + " = " + category.getId(), null);
    }

    public void updateCategory(Category category){
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_NAME, category.getName());
        values.put(SQLite.COLUMN_TYPE, category.getType());
        database.update(SQLite.TABLE_CATEGORY, values, SQLite.COLUMN_ID+"="+category.getId() ,null);
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
        values.put(SQLite.COLUMN_ID_USER, settings.getIdParent());
        values.put(SQLite.COLUMN_AUTO_DELETE, settings.isAutoDeleting() ? 1 : 0 );
        values.put(SQLite.COLUMN_AUTO_SAVINGS, settings.isAutoSaving() ? 1 : 0);
        long insertId = database.insert(SQLite.TABLE_SETTINGS, null,
                values);
        Cursor cursor = database.query(SQLite.TABLE_SETTINGS,
                allColumnsSettings, SQLite.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        settings = new Settings(cursor);
        cursor.close();
        return settings;
    }

    public void deleteSettings(Settings settings){
        database.delete(SQLite.TABLE_SETTINGS, SQLite.COLUMN_ID_USER + " like '" + settings.getIdParent()+"'", null);
    }

    public void updateSettings(Settings settings){
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_ID_USER, settings.getIdParent());
        values.put(SQLite.COLUMN_AUTO_DELETE, settings.isAutoDeleting() ? 1 : 0 );
        values.put(SQLite.COLUMN_AUTO_SAVINGS, settings.isAutoSaving() ? 1 : 0);
        database.update(SQLite.TABLE_SETTINGS, values, SQLite.COLUMN_ID+"="+settings.getId(), null);
    }

    public User getUser(String name, String token){
        Cursor cursor = database.query(SQLite.TABLE_USER, allColumnsUser, SQLite.COLUMN_TOKEN+" like '"+ token +"' and " + SQLite.COLUMN_NAME +" like '"+ name+"'", null, null, null, null);
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
            temp[i] = users.get(i).getName() + "-" +users.get(i).getToken();
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
                allColumnsUser, SQLite.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        user = new User(cursor);
        user.getSettings().setIdParent(user.getId());
        cursor.close();
        return user;
    }

    public void deleteUser(User user){
        database.delete(SQLite.TABLE_USER, SQLite.COLUMN_NAME + " like '" + user.getName() + "' and "+ SQLite.COLUMN_TOKEN +" like '"+ user.getToken() +"'", null);
        //this.deleteSettings(user.getSettings());
    }

    public void updateUser(User user){
        ContentValues values = new ContentValues();
        values.put(SQLite.COLUMN_NAME, user.getName());
        values.put(SQLite.COLUMN_TOKEN, user.getToken());
        values.put(SQLite.COLUMN_SAVINGS, user.getSavings());
        database.update(SQLite.TABLE_USER, values, SQLite.COLUMN_ID+"="+user.getId() ,null);
    }

    public Model getModel(String name, String token){
        Model model = new Model(false);
        model.setUser(getUser(name, token));
        model.setIncome(getCategory(model.getUser().getId(), "INCOME"));
        model.setOutcome(getCategory(model.getUser().getId(), "OUTCOME"));
        return model;
    }

    public Model insertModel(Model model){
        model.setUser(this.insertUser(model.getUser()));
        model.getUser().setSettings(this.insertSettings(model.getUser().getSettings()));
        for(int i = 0; i < model.getOutcome().size(); i++){
            model.getOutcome().set(i, this.insertCategory(model.getOutcome().get(i)));
        }
        for(int i = 0; i < model.getIncome().size(); i++){
            model.getIncome().set(i, this.insertCategory(model.getIncome().get(i)));
        }
        return model;
    }

    public void deleteModel(Model model){
        this.deleteUser(model.getUser());
        /*for(int i = 0; i < model.getOutcome().size(); i++){
            this.deleteCategory(model.getOutcome().get(i));
        }
        for(int i = 0; i < model.getIncome().size(); i++){
            this.deleteCategory(model.getIncome().get(i));
        }*/
    }

    public void updateModel(Model model){
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
    }

}
