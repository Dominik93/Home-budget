package com.slusarzparadowski.model;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.googlecode.charts4j.AxisLabels;
import com.googlecode.charts4j.AxisLabelsFactory;
import com.googlecode.charts4j.AxisStyle;
import com.googlecode.charts4j.AxisTextAlignment;
import com.googlecode.charts4j.BarChart;
import com.googlecode.charts4j.BarChartPlot;
import com.googlecode.charts4j.Color;
import com.googlecode.charts4j.Data;
import com.googlecode.charts4j.DataUtil;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.LinearGradientFill;
import com.googlecode.charts4j.Plots;
import com.slusarzparadowski.database.ModelDataSource;
import com.slusarzparadowski.database.ModelDataSourceMySQL;
import com.slusarzparadowski.database.ModelDataSourceSQLite;
import com.slusarzparadowski.homebudget.R;
import com.slusarzparadowski.model.token.Token;
import com.slusarzparadowski.placeholder.Placeholder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.googlecode.charts4j.Color.BLACK;
import static com.googlecode.charts4j.Color.BLUE;
import static com.googlecode.charts4j.Color.WHITESMOKE;

/**
 * Created by Dominik on 2015-03-22.
 */
public class Model implements IObserver, IBundle{

    private final String USER = "user";
    private final String MODE = "mode";
    private final String INCOME = "income";
    private final String OUTCOME = "outcome";
    private ModelDataSource modelDataSource;
    
    private Map<String, ArrayList<Category> > mapList = new HashMap<String,  ArrayList<Category>>();

    private boolean mode; // true- online false-offline
    private User user;
    private ArrayList<Category> income;
    private ArrayList<Category> outcome;

    private float incomeSum = 0;
    private float outcomeSum = 0;

    private ArrayList<Placeholder> views;

    // <editor-fold defaultstate="collapsed" desc="constructors">

    public Model(boolean mode, Context context) {
        this.mode = mode;
        modelDataSource = new ModelDataSourceSQLite(context);
        this.user = new User();
        this.income = new ArrayList<>();
        this.outcome = new ArrayList<>();
        this.views = new ArrayList<>();
        mapList.put("INCOME", income);
        mapList.put("OUTCOME", outcome);
    }

    public Model(Bundle bundle, Context context){
        Gson gson = Converters.registerLocalDate(new GsonBuilder()).create();
        modelDataSource = new ModelDataSourceSQLite(context);
        this.mode = gson.fromJson(bundle.getString(MODE), boolean.class);
        this.user = gson.fromJson(bundle.getString(USER), User.class);
        this.income = gson.fromJson(bundle.getString(INCOME), new TypeToken<ArrayList<Category>>(){}.getType());
        this.outcome = gson.fromJson(bundle.getString(OUTCOME), new TypeToken<ArrayList<Category>>(){}.getType());
        calculateIncomeSum();
        calculateOutcomeSum();
        this.views = new ArrayList<>();
        mapList.put("INCOME", income);
        mapList.put("OUTCOME", outcome);

    }

    public Model(Model model, Context context) {
        modelDataSource = new ModelDataSourceSQLite(context);
        this.mode = model.isMode();
        this.user = model.getUser();
        this.income = model.getIncome();
        this.outcome = model.getOutcome();
        this.outcomeSum = model.getOutcomeSum();
        this.incomeSum = model.getIncomeSum();
        this.views = new ArrayList<>();
        mapList.put("INCOME", income);
        mapList.put("OUTCOME", outcome);
    }

    public Model(boolean mode, String name) throws IOException {
        this.mode = mode;
        modelDataSource = new ModelDataSourceMySQL();
        Token t = new Token();
        this.user = new User();
        this.user.setName(name);
        while(true){
            t.createToken();
            if(((ModelDataSourceMySQL)modelDataSource).checkToken(t.getToken()).equals("NOT_EXIST")){
                user.setToken(t.getToken());
                break;
            }
        }
        this.loadOutcome();
        this.loadIncome();
        this.calculateIncomeSum();
        this.calculateOutcomeSum();
        this.views = new ArrayList<>();
        mapList.put("INCOME", income);
        mapList.put("OUTCOME", outcome);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="interface IBundle">

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

    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="sync">

    public void syncDatabase(Model model){
        //TODO: file -> database
    }

    public void syncFile(Model model){
        //TODO: database -> file
    }

    //</editor-fold>


    public void createTokenForOfflineUser() throws IOException {
        modelDataSource = new ModelDataSourceMySQL();
        Token t = new Token();
        while(true){
            t.createToken();
            if(((ModelDataSourceMySQL)modelDataSource).checkToken(t.getToken()).equals("NOT_EXIST")){
                user.setToken(t.getToken());
                modelDataSource.insertUser(user);
                break;
            }
        }
    }

    public String generateGraph(Activity context){
        removeSpecialItem(context);
        ArrayList<Float> arrays = new ArrayList<>();
        ArrayList<String> categories = new ArrayList<>();
        for(Category c : this.getOutcome()){
            float categorySum = 0;
            for(Element e : c.getElementList()){
                categorySum += Math.abs(e.getValue());
            }
            arrays.add(categorySum);
            categories.add((c.getName()));
        }
        Data sum = DataUtil.scaleWithinRange(0, Collections.max(arrays), arrays);
        BarChartPlot blue = Plots.newBarChartPlot(sum, BLUE, "SUM");
        BarChart chart = GCharts.newBarChart(blue);

        // Defining axis info and styles
        AxisStyle axisStyle = AxisStyle.newAxisStyle(BLACK, 13, AxisTextAlignment.CENTER);
        AxisLabels categoriesLabel = AxisLabelsFactory.newAxisLabels(categories);
        categoriesLabel.setAxisStyle(axisStyle);
        AxisLabels valueLabel = AxisLabelsFactory.newAxisLabels("Value", 50);
        valueLabel.setAxisStyle(axisStyle);
        AxisLabels value = AxisLabelsFactory.newNumericRangeAxisLabels(0, (Collections.max(arrays)));
        value.setAxisStyle(axisStyle);


        // Adding axis info to chart.
        chart.addXAxisLabels(value);
        chart.addXAxisLabels(valueLabel);
        chart.addYAxisLabels(categoriesLabel);
        chart.addTopAxisLabels(value);
        chart.setHorizontal(true);
        chart.setSize(context.getWindowManager().getDefaultDisplay().getWidth(), categories.size() * 100);
        chart.setSpaceBetweenGroupsOfBars(10);

        chart.setTitle("Graph", BLACK, 16);
        ///51 is the max number of medals.
        chart.setGrid((50.0/(Collections.max(arrays)))*20, 600, 1, 1);
        chart.setBackgroundFill(Fills.newSolidFill(WHITESMOKE));
        LinearGradientFill fill = Fills.newLinearGradientFill(0, Color.newColor("FFEFD5"), 100);
        fill.addColorAndOffset(Color.newColor("DC4800"), 0);
        chart.setAreaFill(fill);
        return chart.toURLString();
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

    public void addSpecialItem(Context context){
        if (!income.contains(new Category(-1, -1, context.getString(R.string.add_category), "ADD")))
            income.add(new Category(-1, -1, context.getString(R.string.add_category), "ADD"));
        for(Category c : income){
            if (!c.getElementList().contains(new Element(-2, -1, context.getString(R.string.show_summary_category))))
                c.getElementList().add(new Element(-2, -1, context.getString(R.string.show_summary_category)));
            if (!c.getElementList().contains(new Element(-1, -1, context.getString(R.string.add_element))))
                c.getElementList().add(new Element(-1, -1, context.getString(R.string.add_element)));
        }

        if (!outcome.contains(new Category(-1, -1, context.getString(R.string.add_category), "ADD")))
            outcome.add(new Category(-1, -1, context.getString(R.string.add_category), "ADD"));
        for(Category c : outcome){
            if (!c.getElementList().contains(new Element(-2, -1, context.getString(R.string.show_summary_category))))
                c.getElementList().add(new Element(-2, -1, context.getString(R.string.show_summary_category)));
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
            if (c.getElementList().contains(new Element(-2, -1, "Show summary")))
                c.getElementList().remove(new Element(-2, -1, "Show summary"));
        }

        if ( outcome.contains(new Category(-1, -1, context.getString(R.string.add_category), "ADD")))
            outcome.remove(new Category(-1, -1, context.getString(R.string.add_category), "ADD"));
        for (Category c : outcome) {
            if (c.getElementList().contains(new Element(-1, -1, context.getString(R.string.add_element))))
                c.getElementList().remove(new Element(-1, -1, context.getString(R.string.add_element)));
            if (c.getElementList().contains(new Element(-2, -1, "Show summary")))
                c.getElementList().remove(new Element(-2, -1, "Show summary"));
        }
    }

    public void loadIncome(){
        this.income = modelDataSource.getCategory(user.getId(), "INCOME");
    }

    public void loadOutcome(){
        this.outcome = modelDataSource.getCategory(user.getId(), "OUTCOME");
    }

    // <editor-fold defaultstate="collapsed" desc="category">

    public void addCategory(Category category, String type){
        openConnection();
        if(mode){

        }
        if(!mode || user.getSettings().isAutoLocalSave()){
            category = modelDataSource.insertCategory(category);
        }
        this.getMapList().get(type).add(category);
        closeConnection();
    }

    public void removeCategory(Category category, String type){
        openConnection();
        if(mode){

        }
        if(!mode || user.getSettings().isAutoLocalSave()){
            modelDataSource.deleteCategory(category);
        }
        closeConnection();
        this.getMapList().get(type).remove(category);
    }

    public void updateCategory(Category category, String type, int index){
        openConnection();
        if(mode){

        }
        if(!mode || user.getSettings().isAutoLocalSave()){
            modelDataSource.updateCategory(category);
        }
        category.setElementList(mapList.get(type).get(index).getElementList());
        mapList.get(type).set(index, category);
        closeConnection();
    }

    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="element">

    public void addElementToCategory(Element element, int category, String type){
        openConnection();
        if(mode){

        }
        if(!mode || user.getSettings().isAutoLocalSave()){
            element = modelDataSource.insertElement(element);
        }
        this.getMapList().get(type).get(category).getElementList().add(element);
        this.calculateOutcomeSum();
        this.calculateIncomeSum();
        closeConnection();
    }

    public void removeElementFromCategory(Element element, int category, String type){
        openConnection();
        if(mode){

        }
        if(!mode || user.getSettings().isAutoLocalSave()){
            modelDataSource.deleteElement(element);
        }
        this.getMapList().get(type).get(category).getElementList().remove(element);
        this.calculateOutcomeSum();
        this.calculateIncomeSum();
        closeConnection();
    }

    public void updateElement(Element element, int index, int category, String type){
        openConnection();
        if(mode){

        }
        if(!mode || user.getSettings().isAutoLocalSave()){
            modelDataSource.updateElement(element);
        }
        mapList.get(type).get(category).getElementList().set(index, element);
        this.calculateOutcomeSum();
        this.calculateIncomeSum();
        closeConnection();
    }

    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getters">

    public String getUSER() {
        return USER;
    }

    public String getMODE() {
        return MODE;
    }

    public String getINCOME() {
        return INCOME;
    }

    public String getOUTCOME() {
        return OUTCOME;
    }

    public ModelDataSource getModelDataSource() {
        return modelDataSource;
    }

    public Map<String, ArrayList<Category>> getMapList() {
        return mapList;
    }

    public boolean isMode() {
        return mode;
    }

    public User getUser() {
        return user;
    }

    public ArrayList<Category> getIncome() {
        return income;
    }

    public ArrayList<Category> getOutcome() {
        return outcome;
    }

    public ArrayList<Placeholder> getViews() {
        return views;
    }

    public float getIncomeSum(){
        return incomeSum;
    }

    public float getOutcomeSum(){
        return outcomeSum;
    }

    public float getSummary(){
        return this.getIncomeSum() - this.getOutcomeSum();
    }

    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="setters">

    public void setModelDataSource(ModelDataSource modelDataSource) {
        this.modelDataSource = modelDataSource;
    }

    public void setMapList(Map<String, ArrayList<Category>> mapList) {
        this.mapList = mapList;
    }

    public void setMode(boolean mode) {
        this.mode = mode;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIncome(ArrayList<Category> income) {
        this.income = income;
    }

    public void setOutcome(ArrayList<Category> outcome) {
        this.outcome = outcome;
    }

    public void setIncomeSum(float incomeSum) {
        this.incomeSum = incomeSum;
    }

    public void setOutcomeSum(float outcomeSum) {
        this.outcomeSum = outcomeSum;
    }

    public void setViews(ArrayList<Placeholder> views) {
        this.views = views;
    }

    //</editor-fold>

    private void openConnection(){
        try {
            modelDataSource.open();
        } catch (SQLException e) {
            Log.i(getClass().getSimpleName(), e.toString());
        }
    }

    private void closeConnection(){
        modelDataSource.close();
    }

    // <editor-fold defaultstate="collapsed" desc="interface IObserver">
    @Override
    public void attachPlaceholder(Placeholder placeholder) {
        this.views.add(placeholder);
    }

    @Override
    public void detachPlaceholder(Placeholder placeholder) {
        this.views.remove(placeholder);
    }

    @Override
    public void notification() {
        for(Placeholder placeholder : views)
            placeholder.update();
    }

    //</editor-fold>
}