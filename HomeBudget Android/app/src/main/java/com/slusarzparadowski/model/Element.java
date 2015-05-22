package com.slusarzparadowski.model;


import android.database.Cursor;

import org.joda.time.LocalDate;

/**
 * Created by Dominik on 2015-03-22.
 */
public class Element extends DatabaseObjectChild {

    private String name;
    private float value;
    private boolean constant;
    private LocalDate date;

    public Element(int id, int idCategory, String name){
        super(id, idCategory);
        this.name = name;
    }

    public Element(Cursor cursor){
        super(cursor.getInt(0), cursor.getInt(1));
        this.name = cursor.getString(2);
        this.value = cursor.getFloat(3);
        this.constant = cursor.getInt(4) == 0 ? false : true;
        if( cursor.getString(5) == null)
            this.date = null;
        else if(cursor.getString(5).equals(""))
            this.date = null;
        else
            this.date = new LocalDate(cursor.getString(5));
    }

    public Element(int id, int idCategory, String name, float value, boolean constant, LocalDate date){
        super(id, idCategory);
        this.name = name;
        this.value = value;
        this.constant = constant;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isConstant() {
        return constant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setConstant(boolean constant) {
        this.constant = constant;
    }

    @Override
    public String toString(){
        return this.name + " "+ ((this.value != 0) ? this.value : " ") + " "+ ((this.date != null) ? this.date : "");
    }

    @Override
    public boolean equals(Object object)
    {
    if(this.getId() == ((Element)object).getId()) {
        return true;
    }
    return false;
    }
}
