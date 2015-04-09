package com.slusarzparadowski.model;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Dominik on 2015-04-01.
 */
public class Settings {

    private final String SETTINGS = "settings";

    private boolean autoSaving;
    private boolean autoDeleting;

    public Settings(){}

    public Settings(boolean autoSaving,  boolean autoDeleting){
        this.autoSaving = autoSaving;
        this.autoDeleting = autoDeleting;
    }

    public boolean isAutoSaving() {
        return autoSaving;
    }

    public void setAutoSaving(boolean autoSaving) {
        this.autoSaving = autoSaving;
    }

    public boolean isAutoDeleting() {
        return autoDeleting;
    }

    public void setAutoDeleting(boolean autoDeleting) {
        this.autoDeleting = autoDeleting;
    }

}
