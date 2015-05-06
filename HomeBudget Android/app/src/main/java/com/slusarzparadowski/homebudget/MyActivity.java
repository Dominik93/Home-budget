package com.slusarzparadowski.homebudget;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

/**
 * Created by Dominik on 2015-04-15.
 */
public abstract class MyActivity extends ActionBarActivity {

    abstract void initElements();
    abstract void initListeners();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initElements();
        //initListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(getClass().getSimpleName(), "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(getClass().getSimpleName(), "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(getClass().getSimpleName(), "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(getClass().getSimpleName(), "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(getClass().getSimpleName(), "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(getClass().getSimpleName(), "onRestart");
    }

}
