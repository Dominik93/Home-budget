package com.slusarzparadowski.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dominik on 2015-04-16.
 */
public class SQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "homebudget.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER =
            "  CREATE TABLE IF NOT EXISTS user(" +
            "  id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
            "  token TEXT NOT NULL," +
            "  savings REAL DEFAULT NULL)";
    private static final String TABLE_CATEGORY =
            "  CREATE TABLE IF NOT EXISTS category (" +
            "  id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ," +
            "  id_user INTEGER DEFAULT NULL," +
            "  name TEXT DEFAULT NULL," +
            "  type TEXT DEFAULT NULL," +
            "  FOREIGN KEY(id_user) REFERENCES user(id))";
    private static final String TABLE_ELEMENT = "" +
            "  CREATE TABLE IF NOT EXISTS element (" +
            "  id INTEGER PRIMARY KEY NOT AUTOINCREMENT NULL ," +
            "  id_category INTEGER NULL DEFAULT NULL," +
            "  name TEXT NULL DEFAULT NULL," +
            "  value REAL NULL DEFAULT NULL," +
            "  const INTEGER NULL DEFAULT NULL," +
            "  date TEXT NULL DEFAULT NULL," +
            "  FOREIGN KEY(id_category) REFERENCES category(id))";
    private static final String TABLE_SETTINGS = "" +
            "  CREATE TABLE IF NOT EXISTS settings (" +
            "  id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "  id_user INTEGER NULL DEFAULT NULL," +
            "  auto_delete INTEGER NULL DEFAULT NULL," +
            "  auto_savings INTEGER NULL DEFAULT NULL," +
            "  FOREIGN KEY(id_user) REFERENCES user(id))";

    public static final String ID = "id";
    public static final String ID_USER = "id_user";
    public static final String ID_CATEGORY = "id_category";
    public static final String TOKEN = "token";
    public static final String SAVINGS = "savings";
    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String VALUE = "value";
    public static final String DATE = "date";
    public static final String CONST = "const";
    public static final String AUTO_DELETE = "auto_delete";
    public static final String AUTO_SAVINGS = "auto_savings";

    public SQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_USER);
        db.execSQL(TABLE_SETTINGS);
        db.execSQL(TABLE_CATEGORY);
        db.execSQL(TABLE_ELEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(this.getClass().getSimpleName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELEMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SETTINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
}
