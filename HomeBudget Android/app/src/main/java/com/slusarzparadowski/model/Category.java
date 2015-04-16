package com.slusarzparadowski.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.slusarzparadowski.database.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik on 2015-03-22.
 */
public class Category {

    private ArrayList<Element> elementList;
    private int id;
    private String name;
    private String type;

    public Category(int id){
        this.id = id;
    }

    public Category(int id, String name, String type){
        this.id = id;
        this.name = name;
        this.type = type;
        this.elementList = new ArrayList<Element>();
    }

    public ArrayList<Element> getElementList() {
        return elementList;
    }

    public void setElementList(ArrayList<Element> elementList) {
        this.elementList = elementList;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString(){
        return this.id +" "+ this.name + " "+ this.type;
    }

    @Override
    public boolean equals(Object object)
    {
        if(this.getId() == ((Category)object).getId()) {
            return true;
        }
        return false;
    }
}
