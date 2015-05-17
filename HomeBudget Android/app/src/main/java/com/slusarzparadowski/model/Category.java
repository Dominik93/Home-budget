package com.slusarzparadowski.model;

import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by Dominik on 2015-03-22.
 */
public class Category extends DatabaseObjectChild {

    private ArrayList<Element> elementList;
    private String name;
    private String type;

    public Category(int id, int idUser, String name, String type){
        super(id, idUser);
        this.name = name;
        this.type = type;
        this.elementList = new ArrayList<>();
    }

    public Category(Cursor cursor) {
        super(cursor.getInt(0), cursor.getInt(1));
        this.name = cursor.getString(2);
        this.type = cursor.getString(3);
        this.elementList = new ArrayList<>();
    }

    public float getSumElement(){
        float sum = 0;
        for(Element e : this.elementList)
            sum += Math.abs(e.getValue());
        return sum;
    }

    public Element getMinElement(){
        int i = 0;
        for (int j = 0; j < this.elementList.size(); j++){
            if(this.elementList.get(i).getValue() > this.elementList.get(j).getValue())
                i = j;
        }
        return this.elementList.get(i);
    }

    public Element getMaxElement(){
        int i = 0;
        for (int j = 0; j < this.elementList.size(); j++){
            if(this.elementList.get(i).getValue() < this.elementList.get(j).getValue())
                i = j;
        }
        return this.elementList.get(i);
    }

    public ArrayList<Element> getElementList() {
        return elementList;
    }

    public void setElementList(ArrayList<Element> elementList) {
        this.elementList = elementList;
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
        return this.name;
        //return this.id +" "+ this.name + " "+ this.type;
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
