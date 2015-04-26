package com.slusarzparadowski.model;

/**
 * Created by Dominik on 2015-04-26.
 */
public abstract class DatabaseObjectParent {

    protected int id;

    public DatabaseObjectParent(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
