package com.slusarzparadowski.model;

/**
 * Created by Dominik on 2015-04-26.
 */
public abstract class DatabaseObjectChild extends DatabaseObjectParent {

    protected int idParent;

    public DatabaseObjectChild(int id, int idParent) {
        super(id);
        this.idParent = idParent;
    }

    public int getIdParent() {
        return idParent;
    }

    public void setIdParent(int idParent) {
        this.idParent = idParent;
    }
}
