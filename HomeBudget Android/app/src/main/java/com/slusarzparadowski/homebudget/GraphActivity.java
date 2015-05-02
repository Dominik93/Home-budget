package com.slusarzparadowski.homebudget;

import android.os.Bundle;
import android.util.Log;
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
    WebView webView;

    @Override
    void initElements() {
        model = new Model(getIntent().getExtras(), getApplicationContext());
        webView = new WebView(this);
        setContentView(webView);
        webView.loadUrl(model.generateGraph(this));
        Log.i(getClass().getSimpleName(), model.generateGraph(this));
    }

    @Override
    void initListeners() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        this.initElements();
        // EXAMPLE CODE END. Use this url string in your web or
        // Internet application.
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
