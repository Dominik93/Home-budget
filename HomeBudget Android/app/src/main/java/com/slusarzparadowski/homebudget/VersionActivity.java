package com.slusarzparadowski.homebudget;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.slusarzparadowski.model.Model;

import org.w3c.dom.Text;


public class VersionActivity extends MyActivity {

    Model model;
    TextView tv;

    @Override
    void initElements() {
        model = new Model(getIntent().getExtras());

        tv = (TextView)findViewById(R.id.textViewTokenValueSettings);
        tv.setText(model.getUser().getToken());
    }

    @Override
    void initListeners() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);
        Log.i(getClass().getSimpleName(), "onCreate");
        this.initElements();
        this.initListeners();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
