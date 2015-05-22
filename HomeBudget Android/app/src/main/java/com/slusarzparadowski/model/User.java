package com.slusarzparadowski.model;

import android.database.Cursor;

/**
 * Created by Dominik on 2015-04-02.
 */
public class User extends DatabaseObjectParent {

    private String name;
    private String token;
    private float savings;
    private Settings settings;

    public User() {
        super(0);
        this.name = null;
        this.token = "OFFLINE MODE";
        this.savings = 0;
        this.settings = new Settings();
    }

    public User(int id, String token, String name, float savings, Settings settings){
        super(id);
        this.token = token;
        this.name = name;
        this.savings = savings;
        this.settings = settings;
    }
    public User(Cursor cursor) {
        super(cursor.getInt(0));
        this.name = cursor.getString(1);
        this.token = cursor.getString(2);
        this.savings = cursor.getFloat(3);
        this.settings = new Settings();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public float getSavings() {
        return savings;
    }

    public void setSavings(float savings) {
        this.savings = savings;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
