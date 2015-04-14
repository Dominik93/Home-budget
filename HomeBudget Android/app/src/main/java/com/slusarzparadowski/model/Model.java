package com.slusarzparadowski.model;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.slusarzparadowski.database.Database;
import com.slusarzparadowski.model.token.Token;
import com.slusarzparadowski.placeholder.Placeholder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Dominik on 2015-03-22.
 */
public class Model implements IObserver, IBundle, IFile{

    private final String USER = "user";
    private final String MODE = "mode";
    private final String INCOME = "income";
    private final String OUTCOME = "outcome";

    private boolean mode; // true- online false-offline
    private User user;
    private ArrayList<Category> income;
    private ArrayList<Category> outcome;
    private ArrayList<Placeholder> views;

    public Model(boolean mode) {
        this.mode = mode;
        this.user = new User();
        this.income = new ArrayList<>();
        this.outcome = new ArrayList<>();
        this.views = new ArrayList<>();
    }

    public Model(Bundle bundle){
        Gson gson = new Gson();
        this.mode = gson.fromJson(bundle.getString(MODE), boolean.class);
        this.user = gson.fromJson(bundle.getString(USER), User.class);
        this.income = gson.fromJson(bundle.getString(INCOME), new TypeToken<ArrayList<Category>>(){}.getType());
        this.outcome = gson.fromJson(bundle.getString(OUTCOME), new TypeToken<ArrayList<Category>>(){}.getType());
        this.views = new ArrayList<>();
    }

    public Model(Model model) {
        this.mode = model.getMode();
        this.user = model.getUser();
        this.income = model.getIncome();
        this.outcome = model.getOutcome();
        this.views = new ArrayList<>();
    }

    public Model(Context context, boolean mode) throws IOException {
        this.mode = mode;
        Token t = new Token(context);
        this.user = Database.getUser(t.getToken());
        this.loadOutcome();
        this.loadIncome();
        this.views = new ArrayList<>();
    }

    @Override
    public Bundle saveToBundle(){
        Bundle bundle = new Bundle();
        Gson gson = new Gson();
        bundle.putString(MODE, gson.toJson(this.mode));
        bundle.putString(USER, gson.toJson(this.user));
        bundle.putString(INCOME, gson.toJson(this.income));
        bundle.putString(OUTCOME, gson.toJson(this.outcome));
        return bundle;
    }

    @Override
    public Bundle addToBundle(Bundle bundle){
        Gson gson = new Gson();
        bundle.putString(MODE, gson.toJson(this.mode));
        bundle.putString(USER, gson.toJson(this.user));
        bundle.putString(INCOME, gson.toJson(this.income));
        bundle.putString(OUTCOME, gson.toJson(this.outcome));
        return bundle;
    }

    @Override
    public boolean loadFromFile(Context context){
        try {
            Gson gson = new Gson();
            FileInputStream fin;
            String temp;
            int c;

            temp="";
            fin = context.openFileInput(MODE);
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            Log.i(getClass().getSimpleName(), "load " +temp);
            fin.close();
            this.mode = gson.fromJson(temp, boolean.class);

            temp="";
            fin = context.openFileInput(USER);
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            Log.i(getClass().getSimpleName(), "load " +temp);
            fin.close();
            this.user = gson.fromJson(temp, User.class);

            temp="";
            fin = context.openFileInput(INCOME);
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            Log.i(getClass().getSimpleName(), "load " +temp);
            fin.close();
            this.income = gson.fromJson(temp, ArrayList.class);

            temp="";
            fin = context.openFileInput(OUTCOME);
            while( (c = fin.read()) != -1){
                temp = temp + Character.toString((char)c);
            }
            Log.i(getClass().getSimpleName(), "load " +temp);
            fin.close();
            this.outcome = gson.fromJson(temp, ArrayList.class);

            Log.i(getClass().getSimpleName(), "load loaded from file");
            return true;
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), "load " + e.toString());
            return false;
        }
    }

    @Override
    public void saveToFile(Context context) throws IOException {
        FileOutputStream fos;
        fos = context.openFileOutput(USER, Context.MODE_PRIVATE);
        fos.write((new Gson().toJson(this.user)).getBytes());
        Log.i(getClass().getSimpleName(), "save "+new Gson().toJson(this.user));
        fos.close();

        fos = context.openFileOutput(MODE, Context.MODE_PRIVATE);
        fos.write((new Gson().toJson(this.mode)).getBytes());
        Log.i(getClass().getSimpleName(), "save "+new Gson().toJson(this.mode));
        fos.close();

        fos = context.openFileOutput(INCOME, Context.MODE_PRIVATE);
        fos.write((new Gson().toJson(this.income)).getBytes());
        Log.i(getClass().getSimpleName(), "save "+new Gson().toJson(this.income));
        fos.close();

        fos = context.openFileOutput(OUTCOME, Context.MODE_PRIVATE);
        fos.write((new Gson().toJson(this.outcome)).getBytes());
        Log.i(getClass().getSimpleName(), "save "+new Gson().toJson(this.outcome));
        fos.close();

        Log.i(getClass().getSimpleName(), "save model saved");
    }

    public void syncDatabase(Model model){
        //TODO: file -> database
    }

    public void syncFile(Model model){
        //TODO: database -> file
    }

    public double getSummary(){
        return this.getIncomeSum() - this.getOutcomeSum();
    }

    public double getIncomeSum(){
        double sum = 0;
        for(Category el : this.income){
            for(Element e : el.getElementList()){
                sum += e.getValue();
            }
        }
        return sum;
    }

    public double getOutcomeSum(){
        double sum = 0;
        for(Category el : this.outcome){
            for(Element e : el.getElementList()){
                sum += e.getValue();
            }
        }
        return sum;
    }

    public void loadIncome(){
        this.income = Database.getList(user.getToken(), "income");
    }

    public void loadOutcome(){
        this.outcome = Database.getList(user.getToken(), "outcome");
    }

    public User getUser(){
        return this.user;
    }

    public boolean getMode() {
        return mode;
    }

    public ArrayList<Category> getIncome() {
        return income;
    }

    public void setIncome(ArrayList<Category> income) {
        this.income = income;
    }

    public ArrayList<Category> getOutcome() {
        return outcome;
    }

    public void setOutcome(ArrayList<Category> outcome) {
        this.outcome = outcome;
    }

    @Override
    public void attachPlaceholder(Placeholder placeholder) {
        this.views.add(placeholder);
        Log.i(getClass().getSimpleName(), "attach view " + placeholder.getClass().getSimpleName());
    }

    @Override
    public void detachPlaceholder(Placeholder placeholder) {
        this.views.remove(placeholder);
        Log.i(getClass().getSimpleName(), "detach view " + placeholder.getClass().getSimpleName());
    }

    @Override
    public void notification() {
        for(Placeholder placeholder : views){
            Log.i(getClass().getSimpleName(), "update view " + placeholder.getClass().getSimpleName());
            placeholder.update(this);
        }
    }

}