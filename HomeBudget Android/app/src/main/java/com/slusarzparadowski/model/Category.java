package com.slusarzparadowski.model;

import android.database.Cursor;
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
    private int idUser;
    private String name;
    private String type;

    public Category(int id){
        this.id = id;
    }

    public Category(int id, int idUser, String name, String type){
        this.id = id;
        this.idUser = idUser;
        this.name = name;
        this.type = type;
        this.elementList = new ArrayList<>();
    }

    public Category(Cursor cursor) {
        this.id = cursor.getInt(0);
        this.idUser = cursor.getInt(1);
        this.name = cursor.getString(2);
        this.type = cursor.getString(3);
        this.elementList = new ArrayList<>();
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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
