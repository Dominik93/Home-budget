package com.slusarzparadowski.model;


import android.util.Log;

import org.joda.time.LocalDate;

import java.util.Date;

/**
 * Created by Dominik on 2015-03-22.
 */
public class Element {

    private int id;
    private String name;
    private float value;
    private boolean constant;
    private String date;

    public Element(int id){
        this.id = id;
    }

    public Element(int id, String name){
        this.id = id;
        this.name = name;
    }

    public Element(int id, String name, float value, boolean constant, String date){
        this.id = id;
        this.name = name;
        this.value = value;
        this.constant = constant;
        try {
            this.date = date;
        }catch (IllegalArgumentException e){
            Log.d(getClass().getSimpleName(), "constructor "+ e.toString());
            this.date = null;
        }
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

    public String toString(){
        if(this.date == null)
            return this.name + " "+ this.value;
        else
            return this.name + " "+ this.value + " "+ this.date;
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
