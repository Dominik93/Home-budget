package com.slusarzparadowski.model;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import com.google.gson.Gson;

import java.io.IOException;

/**
 * Created by Dominik on 2015-04-02.
 */
public class User {

    private final String USER = "user";

    private int id;
    private String name;
    private String token;
    private float savings;
    private Settings settings;

    public User() {
        this.id = 0;
        this.token = "OFFLINE MODE";
        this.savings = 0;
        this.settings = new Settings();
    }

    public User(String token, String name, float savings, Settings settings){
        this.token = token;
        this.name = name;
        this.savings = savings;
        this.settings = settings;
    }

    public User(User user){
        this.id = user.getId();
        this.token = user.getToken();
        this.name = user.getName();
        this.savings = user.getSavings();
        this.settings = user.getSettings();
    }

    public User(Cursor cursor) {
        this.name = cursor.getString(0);
        this.token = cursor.getString(1);
        this.savings = cursor.getFloat(2);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
