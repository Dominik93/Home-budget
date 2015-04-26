package com.slusarzparadowski.homebudget;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.slusarzparadowski.database.ModelDataSource;
import com.slusarzparadowski.model.Model;

import java.sql.SQLException;


public class SettingsActivity extends MyActivity {

    Model model;
    Button b1,b2;
    CheckBox cb1,cb2;
    ModelDataSource modelDataSource;

    @Override
    void initElements() {
        this.model = new Model(getIntent().getExtras());
        this.modelDataSource = new ModelDataSource(getApplicationContext());

        this.b1 = (Button)findViewById(R.id.buttonSettingsSave);
        this.b2 = (Button)findViewById(R.id.buttonSettingsCancel);

        this.cb1 = (CheckBox)findViewById(R.id.checkBoxSettingsSavings);
        this.cb2 = (CheckBox)findViewById(R.id.checkBoxSettingsDelete);
        this.cb1.setChecked(this.model.getUser().getSettings().isAutoSaving());
        this.cb2.setChecked(this.model.getUser().getSettings().isAutoDeleting());
    }

    @Override
    void initListeners() {
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(getClass().getSimpleName(), "onClick");
                try {
                    modelDataSource.open();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                modelDataSource.updateSettings(model.getUser().getSettings());
                modelDataSource.close();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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
