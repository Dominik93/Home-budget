package com.slusarzparadowski.homebudget;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.slusarzparadowski.dialog.InternetAccessDialog;
import com.slusarzparadowski.model.Model;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

//  cd C:\Users\Dominik\AppData\Local\Android\sdk\platform-tools

public class WelcomeActivity extends ActionBarActivity {

    ProgressDialog pDialog;
    Model model;
    Button b1, b2;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.activity = this;
        Log.i(getClass().getSimpleName(), "onCreate");
        b1 = (Button)findViewById(R.id.buttonOnline);
        b2 = (Button)findViewById(R.id.buttonOffline);
        getApplication();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(getClass().getSimpleName(), "onClick b1 online");
                new CheckInternetAccess().execute();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(getClass().getSimpleName(), "onClick b2 offline");
                new LoadModelFromFile().execute();
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

    class CheckInternetAccess extends AsyncTask<Void, String, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(getClass().getSimpleName(), "onPreExecute");
            pDialog = new ProgressDialog(WelcomeActivity.this);
            pDialog.setMessage("Checking internet connection...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected Boolean doInBackground(Void... args) {
            return hasActiveInternetConnection();
        }

        protected void onPostExecute(Boolean return_value) {
            Log.i(getClass().getSimpleName(), "onPostExecute "+ return_value);
            if(return_value){
                pDialog.setMessage("Load model form database...");
                new LoadModelFromDatabase().execute();
            }
            else{
                Log.i(getClass().getSimpleName(), "onPostExecute No internet access turn on wifi or 3G");
                pDialog.dismiss();
                new InternetAccessDialog(activity, LayoutInflater.from(getApplicationContext()).inflate(R.layout.prompts_internet, null)).buildDialog().show();
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
                model = new Model(getApplicationContext(), true);
                return true;
            } catch (IOException e) {
                Log.e(getClass().getSimpleName(), "doInBackground " + e.toString());
                return false;
            }
        }

        protected void onPostExecute(Boolean return_value) {
            Log.i(getClass().getSimpleName(), "onPostExecute "+ return_value);
            pDialog.dismiss();
            if(return_value){
                Intent intent = new Intent(getApplication(), MainActivity.class);
                intent.putExtras(model.saveToBundle());
                startActivity(intent);
            }
        }

    }

    class LoadModelFromFile extends AsyncTask<String, String, Boolean> {

        private Gson gson = new Gson();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(getClass().getSimpleName(), "onPreExecute");
            pDialog = new ProgressDialog(WelcomeActivity.this);
            pDialog.setMessage("Load model from file...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected Boolean doInBackground(String... args) {
            Log.i(getClass().getSimpleName(), "doInBackground");
            Log.i(getClass().getSimpleName(), "doInBackground created new model");
            model = new Model(false);
            Log.i(getClass().getSimpleName(), "doInBackground try load model");
            if(model.load(getApplicationContext())){
                return true;
            }
            else{
                try {
                    Log.i(getClass().getSimpleName(), "doInBackground model save");
                    model.save(getApplicationContext());
                    return true;
                } catch (IOException e) {
                    Log.e(getClass().getSimpleName(), "doInBackground " + e.toString());
                    return false;
                }
            }
        }

        protected void onPostExecute(Boolean return_value) {
            Log.i(getClass().getSimpleName(), "onPostExecute "+ return_value);
            pDialog.dismiss();
            if(return_value){
                Intent intent = new Intent(getApplication(), MainActivity.class);
                intent.putExtras(model.saveToBundle());
                startActivity(intent);
            }
        }

    }

}
