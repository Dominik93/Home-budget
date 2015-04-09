package com.slusarzparadowski.homebudget;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.slusarzparadowski.model.Model;


public class SettingsActivity extends ActionBarActivity {

    Model model;
    Button b1,b2;
    CheckBox cb1,cb2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.i(getClass().getSimpleName(), "onCreate");
        this.model = new Model(getIntent().getExtras());
        b1 = (Button)findViewById(R.id.buttonSettingsSave);
        b2 = (Button)findViewById(R.id.buttonSettingsCancel);

        cb1 = (CheckBox)findViewById(R.id.checkBoxSettingsSavings);
        cb2 = (CheckBox)findViewById(R.id.checkBoxSettingsDelete);
        cb1.setChecked(this.model.getUser().getSettings().isAutoSaving());
        cb2.setChecked(this.model.getUser().getSettings().isAutoDeleting());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(getClass().getSimpleName(), "onClick");
                Intent returnIntent = new Intent();
                returnIntent.putExtras(model.saveToBundle());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(getClass().getSimpleName(), "onClick");
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(getClass().getSimpleName(), "onCheckedChanged "+isChecked);
                model.getUser().getSettings().setAutoSaving(isChecked);
            }
        });
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(getClass().getSimpleName(), "onCheckedChanged " +isChecked);
                model.getUser().getSettings().setAutoDeleting(isChecked);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(getClass().getSimpleName(), "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(getClass().getSimpleName(), "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(getClass().getSimpleName(), "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(getClass().getSimpleName(), "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(getClass().getSimpleName(), "onDestroy");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.i(getClass().getSimpleName(), "onRestart");
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
