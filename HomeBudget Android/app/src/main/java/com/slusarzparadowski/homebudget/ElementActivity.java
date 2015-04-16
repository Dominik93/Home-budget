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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.slusarzparadowski.model.Element;
import com.slusarzparadowski.model.Model;


public class ElementActivity extends MyActivity {

    Model model;
    CheckBox cb1, cb2;
    DatePicker dp;

    Button b1,b2;
    EditText et1, et2;

    int category;
    int element;
    String type;
    boolean modify;

    @Override
    void initElements() {
        this.model = new Model(getIntent().getExtras());

        et1 = (EditText)findViewById(R.id.editTextElementName);
        et2 = (EditText)findViewById(R.id.editTextElementValue);

        b1 = (Button)findViewById(R.id.buttonElementSave);
        b2 = (Button)findViewById(R.id.buttonElementCancel);

        cb2 = (CheckBox)findViewById(R.id.checkBoxElementConstant);
        cb1 = (CheckBox)findViewById(R.id.checkBoxElementDate);

        dp = (DatePicker) findViewById(R.id.datePickerElementDate);
    }

    @Override
    void initListeners() {
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(getClass().getSimpleName(), "onCheckedChanged "+isChecked);
                if(isChecked)
                    dp.setVisibility(View.VISIBLE);
                else
                    dp.setVisibility(View.GONE);
            }
        });
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(getClass().getSimpleName(), "onCheckedChanged "+isChecked);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(getClass().getSimpleName(), "onClick ");
                Intent returnIntent = new Intent();
                Element element = new Element(0, et1.getText().toString(), Float.valueOf(et2.getText().toString()), cb2.isChecked(), dp.toString());
                if(type.equals("INCOME"))
                    model.getIncome().get(category).getElementList().add(element);
                else if(type.equals("OUTCOME"))
                    model.getOutcome().get(category).getElementList().add(element);
                returnIntent.putExtras(model.saveToBundle());
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(getClass().getSimpleName(), "onClick ");
                Intent returnIntent = new Intent();
                setResult(RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element);
        this.initElements();
        this.initListeners();
        //TODO: 2 stany co zrobić jak chce modyfikować a co jak dodać co przekazywać do tego? element liste model czy co?

        modify = getIntent().getExtras().getBoolean("modify");

        if(modify){
            category = getIntent().getExtras().getInt("category");
            element = getIntent().getExtras().getInt("element");
        }
        else{
            category = getIntent().getExtras().getInt("category");
            type = getIntent().getExtras().getString("type");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_element, menu);
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
