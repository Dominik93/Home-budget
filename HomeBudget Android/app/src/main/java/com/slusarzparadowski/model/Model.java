package com.slusarzparadowski.model;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.slusarzparadowski.database.Database;
import com.slusarzparadowski.homebudget.R;
import com.slusarzparadowski.model.token.Token;
import com.slusarzparadowski.placeholder.Placeholder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dominik on 2015-03-22.
 */
public class Model implements IObserver, IBundle{

    private final String USER = "user";
    private final String MODE = "mode";
    private final String INCOME = "income";
    private final String OUTCOME = "outcome";

    private Map<String,  ArrayList<Category> > map = new HashMap<String,  ArrayList<Category>>();

    private boolean mode; // true- online false-offline
    private User user;
    private ArrayList<Category> income;
    private ArrayList<Category> outcome;

    private float incomeSum = 0;
    private float outcomeSum = 0;

    private ArrayList<Placeholder> views;

    public Model(boolean mode) {
        this.mode = mode;
        this.user = new User();
        this.income = new ArrayList<>();
        this.outcome = new ArrayList<>();
        this.views = new ArrayList<>();
        map.put("INCOME", income);
        map.put("OUTCOME", outcome);
    }

    public Model(Bundle bundle){
        Gson gson = Converters.registerLocalDate(new GsonBuilder()).create();
        this.mode = gson.fromJson(bundle.getString(MODE), boolean.class);
        this.user = gson.fromJson(bundle.getString(USER), User.class);
        this.income = gson.fromJson(bundle.getString(INCOME), new TypeToken<ArrayList<Category>>(){}.getType());
        this.outcome = gson.fromJson(bundle.getString(OUTCOME), new TypeToken<ArrayList<Category>>(){}.getType());
        calculateIncomeSum();
        calculateOutcomeSum();
        this.views = new ArrayList<>();
        map.put("INCOME", income);
        map.put("OUTCOME", outcome);

    }

    public Model(Model model) {
        this.mode = model.getMode();
        this.user = model.getUser();
        this.income = model.getIncome();
        this.outcome = model.getOutcome();
        this.outcomeSum = model.getOutcomeSum();
        this.incomeSum = model.getIncomeSum();
        this.views = new ArrayList<>();
        map.put("INCOME", income);
        map.put("OUTCOME", outcome);
    }

    public Model(Context context, boolean mode) throws IOException {
        this.mode = mode;
        Token t = new Token(context);
        this.user = Database.getUser(t.getToken());
        this.loadOutcome();
        this.loadIncome();
        calculateIncomeSum();
        calculateOutcomeSum();
        this.views = new ArrayList<>();
        map.put("INCOME", income);
        map.put("OUTCOME", outcome);
    }

    @Override
    public Bundle saveToBundle(){
        Bundle bundle = new Bundle();
        Gson gson = Converters.registerLocalDate(new GsonBuilder()).create();
        bundle.putString(MODE, gson.toJson(this.mode));
        bundle.putString(USER, gson.toJson(this.user));
        bundle.putString(INCOME, gson.toJson(this.income));
        bundle.putString(OUTCOME, gson.toJson(this.outcome));
        return bundle;
    }

    @Override
    public Bundle addToBundle(Bundle bundle){
        Gson gson = Converters.registerLocalDate(new GsonBuilder()).create();
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

    public float getSummary(){
        return this.getIncomeSum() - this.getOutcomeSum();
    }

    public void calculateIncomeSum(){
        for (Category c : income){
            for(Element e : c.getElementList())
                incomeSum += e.getValue();
        }
    }

    public void calculateOutcomeSum(){
        for (Category c : outcome){
            for(Element e : c.getElementList())
                outcomeSum += e.getValue();
        }
    }

    public float getIncomeSum(){
        return incomeSum;
    }

    public float getOutcomeSum(){
        return outcomeSum;
    }

    public void addSpecialItem(Context context){
        if (!income.contains(new Category(-1, -1, context.getString(R.string.add_category), "ADD")))
            income.add(new Category(-1, -1, context.getString(R.string.add_category), "ADD"));
        for(Category c : income){
            if (!c.getElementList().contains(new Element(-1, -1, context.getString(R.string.add_element))))
                c.getElementList().add(new Element(-1, -1, context.getString(R.string.add_element)));
        }

        if (!outcome.contains(new Category(-1, -1, context.getString(R.string.add_category), "ADD")))
            outcome.add(new Category(-1, -1, context.getString(R.string.add_category), "ADD"));
        for(Category c : outcome){
            if (!c.getElementList().contains(new Element(-1, -1, context.getString(R.string.add_element))))
                c.getElementList().add(new Element(-1, -1, context.getString(R.string.add_element)));
        }
    }

    public void removeSpecialItem(Context context){
        if ( income.contains(new Category(-1, -1, context.getString(R.string.add_category), "ADD")))
            income.remove(new Category(-1, -1, context.getString(R.string.add_category), "ADD"));
        for (Category c : income) {
            if (c.getElementList().contains(new Element(-1, -1, context.getString(R.string.add_element))))
                c.getElementList().remove(new Element(-1, -1, context.getString(R.string.add_element)));
        }

        if ( outcome.contains(new Category(-1, -1, context.getString(R.string.add_category), "ADD")))
            outcome.remove(new Category(-1, -1, context.getString(R.string.add_category), "ADD"));
        for (Category c : outcome) {
            if (c.getElementList().contains(new Element(-1, -1, context.getString(R.string.add_element))))
                c.getElementList().remove(new Element(-1, -1, context.getString(R.string.add_element)));
        }
    }

    public void addElement(){

    }

    public void addCategory(Category category, String type){
        this.getMap().get(type).add(category);
    }

    public void addElementToCategory(Element element, int category, String type){
        this.getMap().get(type).get(category).getElementList().add(element);
    }

    public void deleteCategory(int category, String type){
        this.getMap().get(type).remove(category);
    }

    public void deleteCategory(Category category, String type){
        this.getMap().get(type).remove(category);
    }

    public void removeElementToCategory(Element element, int category, String type){
        this.getMap().get(type).get(category).getElementList().remove(element);
    }

    public void removeElementToCategory(int element, int category, String type){
        this.getMap().get(type).get(category).getElementList().remove(element);
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

    public Map<String, ArrayList<Category>> getMap() {
        return map;
    }

    public void setMap(Map<String, ArrayList<Category>> map) {
        this.map = map;
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