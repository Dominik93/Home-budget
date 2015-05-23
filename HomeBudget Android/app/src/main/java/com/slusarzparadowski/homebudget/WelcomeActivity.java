package com.slusarzparadowski.homebudget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.slusarzparadowski.database.ModelDataSource;
import com.slusarzparadowski.database.ModelDataSourceMySQL;
import com.slusarzparadowski.database.ModelDataSourceSQLite;
import com.slusarzparadowski.dialog.NotificationDialog;
import com.slusarzparadowski.model.Model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;

//  cd C:\Users\Dominik\AppData\Local\Android\sdk\platform-tools

public class WelcomeActivity extends MyActivity {

    ProgressDialog progressDialog;
    Model model;
    EditText editText;
    Button b1, b2, b3;
    Activity activity;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Log.i(getClass().getSimpleName(), "onCreate");

        this.initElements();
        this.initListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    void initElements() {
        this.activity = this;
        model = new Model(false, getApplicationContext());
        b1 = (Button)findViewById(R.id.buttonOnline);
        b2 = (Button)findViewById(R.id.buttonOffline);
        b3 = (Button)findViewById(R.id.buttonDelete);
        editText = (EditText)findViewById(R.id.editTextUserName);
        spinner = (Spinner) findViewById(R.id.spinnerUsers);
        try {
            spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, model.getUsers()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if((spinner.getSelectedItem()).toString().equals("Add user")){
            editText.setVisibility(View.VISIBLE);
            b3.setVisibility(View.GONE);
        }
        else{
            editText.setVisibility(View.GONE);
            b3.setVisibility(View.VISIBLE);
        }
    }

    @Override
    void initListeners() {
        this.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if((spinner.getSelectedItem()).toString().equals("Add user")){
                    editText.setVisibility(View.VISIBLE);
                    b3.setVisibility(View.GONE);
                }
                else{
                    editText.setVisibility(View.GONE);
                    b3.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(getClass().getSimpleName(), "onClick b1 online");

                new CheckInternetAccess().execute();
            }
        });
        this.b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(getClass().getSimpleName(), "onClick b2 offline");

                new LoadModelFromFile().execute();
            }
        });
        this.b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(getClass().getSimpleName(), "onClick b3 delete");
                if(!spinner.getSelectedItem().toString().equals("Add user")){
                    try {
                        model.deleteModel(model.getModel(spinner.getSelectedItem().toString().split("-")[0], spinner.getSelectedItem().toString().split("-")[1]));
                        spinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, model.getUsers()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if((spinner.getSelectedItem()).toString().equals("Add user")){
                        editText.setVisibility(View.VISIBLE);
                    }
                    else{
                        editText.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    class CheckInternetAccess extends AsyncTask<Void, String, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(getClass().getSimpleName(), "onPreExecute");
            progressDialog = new ProgressDialog(WelcomeActivity.this);
            progressDialog.setMessage("Checking internet connection...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        protected Boolean doInBackground(Void... args) {
            return hasActiveInternetConnection();
        }

        protected void onPostExecute(Boolean return_value) {
            Log.i(getClass().getSimpleName(), "onPostExecute "+ return_value);
            if(return_value){
                progressDialog.setMessage("Load model form database...");
                new LoadModelFromDatabase().execute();
            }
            else{
                Log.i(getClass().getSimpleName(), "onPostExecute No internet access turn on wifi or 3G");
                progressDialog.dismiss();
                new NotificationDialog(activity, R.layout.notification_dialog, getString(R.string.connection_error)).buildDialog().show();
            }

        }

        private boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null;
        }

        private boolean hasActiveInternetConnection() {
            if (isNetworkAvailable()) {
                try {
                    HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                    urlc.setRequestProperty("User-Agent", "Test");
                    urlc.setRequestProperty("Connection", "close");
                    urlc.setConnectTimeout(1500);
                    urlc.connect();
                    Log.d(getClass().getSimpleName(), "hasActiveInternetConnection Active connection");
                    return (urlc.getResponseCode() == 200);
                } catch (IOException e) {
                    Log.e(getClass().getSimpleName(), "hasActiveInternetConnection Error checking internet connection", e);
                }
            } else {
                Log.d(getClass().getSimpleName(), "hasActiveInternetConnection No network available!");
            }
            return false;
        }

    }

    class LoadModelFromDatabase extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(getClass().getSimpleName(), "onPreExecute");

        }

        protected Boolean doInBackground(String... args) {
            try {
                if((spinner.getSelectedItem()).toString().equals("Add user")){
                    model = new Model(false, editText.getText().toString(), getApplicationContext());
                    model.insertModel();
                    model.setMode(true);
                    model.insertModel();
                    return true;
                }
                else{
                    if(spinner.getSelectedItem().toString().split("-")[1].equals("OFFLINE MODE")){
                        model = model.getModel(spinner.getSelectedItem().toString().split("-")[0], spinner.getSelectedItem().toString().split("-")[1]);
                        model.createTokenForOfflineUser();
                        model.updateUser();
                        model.setMode(true);
                        model.insertModel();
                        model = model.getModel(spinner.getSelectedItem().toString().split("-")[0], spinner.getSelectedItem().toString().split("-")[1]);
                    }
                    else{
                        model.setMode(true);
                        model = model.getModel(spinner.getSelectedItem().toString().split("-")[0], spinner.getSelectedItem().toString().split("-")[1]);
                    }
                    return true;
                }
            }catch (IOException | SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        protected void onPostExecute(Boolean return_value) {
            Log.i(getClass().getSimpleName(), "onPostExecute "+ return_value);
            progressDialog.dismiss();
            if(return_value){
                Intent intent = new Intent(getApplication(), MainActivity.class);
                intent.putExtras(model.saveToBundle());
                startActivity(intent);
            }
        }

    }

    class LoadModelFromFile extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(getClass().getSimpleName(), "onPreExecute");
            progressDialog = new ProgressDialog(WelcomeActivity.this);
            progressDialog.setMessage("Load model from file...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        protected Boolean doInBackground(String... args) {
            Log.i(getClass().getSimpleName(), "doInBackground");
            try {
                if((spinner.getSelectedItem()).toString().equals("Add user")){
                    model.getUser().setName(editText.getText().toString());
                    model.insertModel();
                }
                else {
                    model = model.getModel(spinner.getSelectedItem().toString().split("-")[0],
                            spinner.getSelectedItem().toString().split("-")[1]);
                }
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

        protected void onPostExecute(Boolean return_value) {
            Log.i(getClass().getSimpleName(), "onPostExecute "+ return_value);
            progressDialog.dismiss();
            if(return_value){
                Intent intent = new Intent(getApplication(), MainActivity.class);
                intent.putExtras(model.saveToBundle());
                startActivity(intent);
            }
        }

    }

}
