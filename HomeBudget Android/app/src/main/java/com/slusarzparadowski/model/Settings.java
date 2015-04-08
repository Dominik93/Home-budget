package com.slusarzparadowski.model;

/**
 * Created by Dominik on 2015-04-01.
 */
public class Settings {

    private boolean autoSaving;
    private boolean autoDeleting;

    public Settings(){}

    public Settings(boolean autoSaving,  boolean autoDeleting){
        this.autoSaving = autoSaving;
        this.autoDeleting = autoDeleting;
    }
}
