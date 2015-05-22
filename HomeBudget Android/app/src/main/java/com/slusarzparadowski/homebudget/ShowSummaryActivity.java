package com.slusarzparadowski.homebudget;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.slusarzparadowski.model.Model;


public class ShowSummaryActivity extends MyActivity {

    TextView tv0, tv1, tv2, tv3, tv4;
    Model model;
    String type;
    int category;

    @Override
    void initElements() {
        this.model = new Model(getIntent().getExtras(), getApplicationContext());
        this.model.removeSpecialItem();
        category = getIntent().getExtras().getInt("category");
        type = getIntent().getExtras().getString("type");

        tv0 = (TextView) findViewById(R.id.textViewCategory);
        tv1 = (TextView) findViewById(R.id.textViewMaxElementValue);
        tv2 = (TextView) findViewById(R.id.textViewMinElementValue);
        tv3 = (TextView) findViewById(R.id.textViewPartAllValue);
        tv4 = (TextView) findViewById(R.id.textViewSumAllElementsValue);

        tv0.setText(tv0.getText() + " "+ model.getMapList().get(type).get(category).getName());
        tv1.setText(model.getMapList().get(type).get(category).getMaxElement().toString());
        tv2.setText(model.getMapList().get(type).get(category).getMinElement().toString());
        tv4.setText(String.valueOf(model.getMapList().get(type).get(category).getSumElement()));

        float percent = 0;
        if(type.equals("INCOME")){
            percent = model.getMapList().get(type).get(category).getSumElement() / model.getIncomeSum();
        }
        else if(type.equals("OUTCOME")){
            percent = model.getMapList().get(type).get(category).getSumElement() / model.getOutcomeSum();
        }
        tv3.setText(String.valueOf((int)(percent*100)) +"%");
    }

    @Override
    void initListeners() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_summary);
        initElements();
        initListeners();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_summary, menu);
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
