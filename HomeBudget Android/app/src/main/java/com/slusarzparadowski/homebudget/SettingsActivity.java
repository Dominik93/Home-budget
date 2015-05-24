package com.slusarzparadowski.homebudget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.slusarzparadowski.database.ModelDataSourceSQLite;
import com.slusarzparadowski.model.Model;

import java.sql.SQLException;


public class SettingsActivity extends MyActivity {

    Model model;
    Button button1, button2;
    CheckBox checkBox1, checkBox2, checkBox3;
    ModelDataSourceSQLite modelDataSourceSQLite;

    @Override
    void initElements() {
        this.model = new Model(getIntent().getExtras(), getApplicationContext());
        this.modelDataSourceSQLite = new ModelDataSourceSQLite(getApplicationContext());

        this.button1 = (Button)findViewById(R.id.buttonSettingsSave);
        this.button2 = (Button)findViewById(R.id.buttonSettingsCancel);

        this.checkBox1 = (CheckBox)findViewById(R.id.checkBoxSettingsSavings);
        this.checkBox2 = (CheckBox)findViewById(R.id.checkBoxSettingsDelete);
        this.checkBox3 = (CheckBox)findViewById(R.id.checkBoxLocalSave);
        this.checkBox1.setChecked(this.model.getUser().getSettings().isAutoSaving());
        this.checkBox2.setChecked(this.model.getUser().getSettings().isAutoDeleting());
        this.checkBox3.setChecked(this.model.getUser().getSettings().isAutoLocalSave());
    }

    @Override
    void initListeners() {
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.i(getClass().getSimpleName(), "onClick");
                    Intent returnIntent = new Intent();
                    model.updateSettings();
                    returnIntent.putExtras(model.saveToBundle());
                    setResult(RESULT_OK, returnIntent);
                    finish();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(getClass().getSimpleName(), "onClick");
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(getClass().getSimpleName(), "onCheckedChanged "+isChecked);
                model.getUser().getSettings().setAutoSaving(isChecked);
                Toast.makeText(getApplicationContext(), "Recommended with auto deleting", Toast.LENGTH_SHORT).show();
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(getClass().getSimpleName(), "onCheckedChanged " +isChecked);
                model.getUser().getSettings().setAutoDeleting(isChecked);
                Toast.makeText(getApplicationContext(), "Recommended with auto savings", Toast.LENGTH_SHORT).show();
            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(getClass().getSimpleName(), "onCheckedChanged " +isChecked);
                model.getUser().getSettings().setAutoLocalSave(isChecked);
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
