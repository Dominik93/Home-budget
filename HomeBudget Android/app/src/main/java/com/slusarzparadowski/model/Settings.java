package com.slusarzparadowski.model;

import android.database.Cursor;

/**
 * Created by Dominik on 2015-04-01.
 */
public class Settings extends DatabaseObjectChild {

    private boolean autoSaving;
    private boolean autoDeleting;
    private boolean autoLocalSave;


    public Settings(){
        super(0, 0);
        this.autoSaving = false;
        this.autoDeleting = false;
        this.autoLocalSave = false;
    }

    public Settings(boolean autoSaving,  boolean autoDeleting, boolean autoLocalSave){
        super(0, 0);
        this.autoSaving = autoSaving;
        this.autoDeleting = autoDeleting;
        this.autoLocalSave = autoLocalSave;
    }

    public Settings(Cursor cursor) {
        super(cursor.getInt(0), cursor.getInt(1));
        this.autoSaving = cursor.getInt(2) == 0 ? false : true;
        this.autoDeleting = cursor.getInt(3) == 0 ? false : true;
        this.autoLocalSave = cursor.getInt(4) == 0 ? false : true;
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

    public boolean isAutoLocalSave() {
        return autoLocalSave;
    }

    public void setAutoLocalSave(boolean autoLocalSave) {
        this.autoLocalSave = autoLocalSave;
    }
}
