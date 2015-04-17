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
public class Model implements IObserver, IBundle{

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

    public boolean isMode() {
        return mode;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public void setUser(User user) {
        this.user = user;
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