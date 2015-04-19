package com.slusarzparadowski.model;


import android.database.Cursor;
import android.util.Log;

import org.joda.time.LocalDate;

import java.util.Date;

/**
 * Created by Dominik on 2015-03-22.
 */
public class Element {

    private int id;
    private int idCategory;
    private String name;
    private float value;
    private boolean constant;
    private LocalDate date;

    public Element(int id){
        this.id = id;
    }

    public Element(int id, int idCategory, String name){
        this.id = id;
        this.name = name;
        this.idCategory = idCategory;
    }

    public Element(Cursor cursor){
        this.id = cursor.getInt(0);
        this.idCategory = cursor.getInt(1);
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

    public Element(int id, String name, float value, boolean constant, LocalDate date){
        this.id = id;
        this.name = name;
        this.value = value;
        this.constant = constant;
        this.date = date;
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

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public String toString(){
        return this.id +" "+ this.name + " "+ ((this.value != 0) ? this.value : " ") + " "+ ((this.date != null) ? this.date : "");
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
