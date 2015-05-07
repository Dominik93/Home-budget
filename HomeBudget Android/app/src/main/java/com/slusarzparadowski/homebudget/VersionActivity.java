package com.slusarzparadowski.homebudget;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.slusarzparadowski.dialog.TokenDialog;
import com.slusarzparadowski.model.Model;


public class VersionActivity extends MyActivity {

    Model model;
    TextView textView0, textView1;
    Activity activity;

    @Override
    void initElements() {
        activity = this;
        model = new Model(getIntent().getExtras(), getApplicationContext());
        textView0 = (TextView)findViewById(R.id.textViewTokenValue);
        textView0.setText(model.getUser().getToken());
        textView1 = (TextView)findViewById(R.id.textViewToken);
    }

    @Override
    void initListeners() {
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TokenDialog(activity, R.layout.prompts_token, model.getUser()).buildDialog().show();
            }
        });
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
