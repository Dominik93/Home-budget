package com.slusarzparadowski.homebudget;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.googlecode.charts4j.*;
import com.slusarzparadowski.model.Category;
import com.slusarzparadowski.model.Element;
import com.slusarzparadowski.model.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Logger;

import static com.googlecode.charts4j.Color.*;

public class GraphActivity extends MyActivity {

    Model model;

    @Override
    void initElements() {

    }

    @Override
    void initListeners() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        model = new Model(getIntent().getExtras(), getApplicationContext());
        model.removeSpecialItem(getApplicationContext());
        ArrayList<Float> arrays = new ArrayList<>();
        ArrayList<String> categories = new ArrayList<>();
        for(Category c : model.getOutcome()){
            float categorySum = 0;
            for(Element e : c.getElementList()){
                categorySum += Math.abs(e.getValue());
            }
            arrays.add(categorySum);
            categories.add((c.getName()));
        }
        Data sum = DataUtil.scaleWithinRange(0,  Math.abs(Collections.min(arrays)), arrays);
        BarChartPlot red = Plots.newBarChartPlot(sum, RED, "SUM");
        BarChart chart = GCharts.newBarChart(red);

        // Defining axis info and styles
        AxisStyle axisStyle = AxisStyle.newAxisStyle(BLACK, 13, AxisTextAlignment.CENTER);
        AxisLabels country = AxisLabelsFactory.newAxisLabels("Categories", Math.abs(Collections.min(arrays)));
        country.setAxisStyle(axisStyle);
        AxisLabels countries = AxisLabelsFactory.newAxisLabels(categories);
        countries.setAxisStyle(axisStyle);
        AxisLabels medals = AxisLabelsFactory.newAxisLabels("Value",  Math.abs(Collections.min(arrays)));
        medals.setAxisStyle(axisStyle);
        AxisLabels medalCount = AxisLabelsFactory.newNumericRangeAxisLabels(0,  Math.abs(Collections.min(arrays)));
        medalCount.setAxisStyle(axisStyle);


        // Adding axis info to chart.
        chart.addXAxisLabels(medalCount);
        chart.addXAxisLabels(medals);
        chart.addYAxisLabels(countries);
        chart.addYAxisLabels(country);
        chart.addTopAxisLabels(medalCount);
        chart.setHorizontal(true);
        chart.setSize(450, 650);
        chart.setSpaceBetweenGroupsOfBars(30);

        chart.setTitle("Graph", BLACK, 16);
        ///51 is the max number of medals.
        chart.setGrid((50.0/ Math.abs(Collections.min(arrays)))*20, 600, 3, 2);
        chart.setBackgroundFill(Fills.newSolidFill(LIGHTGREY));
        LinearGradientFill fill = Fills.newLinearGradientFill(0, Color.newColor("E37600"), 100);
        fill.addColorAndOffset(Color.newColor("DC4800"), 0);
        chart.setAreaFill(fill);
        String url = chart.toURLString();
        // EXAMPLE CODE END. Use this url string in your web or
        // Internet application.
        WebView webView = new WebView( this );
        setContentView( webView );
        webView.loadUrl( url );
        Logger.global.info(url);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_graph, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
