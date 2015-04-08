package com.slusarzparadowski.model;

/**
 * Created by Dominik on 2015-04-02.
 */
public class User {

    private String token;
    private float savings;
    private Settings settings;

    public User() {
        this.token = "OFFLINE MODE";
        this.savings = 0;
        this.settings = new Settings();
    }

    public User(String token, float savings, Settings settings){
        this.token = token;
        this.savings = savings;
        this.settings = settings;
    }

    public User(User user){
        this.token = user.getToken();
        this.savings = user.getSavings();
        this.settings = user.getSettings();
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
}
