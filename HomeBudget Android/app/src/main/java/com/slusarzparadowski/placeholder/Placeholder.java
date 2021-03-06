package com.slusarzparadowski.placeholder;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.google.gson.Gson;
import com.slusarzparadowski.adapter.ExpandableItemListAdapter;
import com.slusarzparadowski.homebudget.MainActivity;
import com.slusarzparadowski.homebudget.R;
import com.slusarzparadowski.model.Model;

/**
 * Created by Dominik on 2015-04-05.
 */
public abstract class Placeholder extends Fragment implements IObservable {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    protected static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        Log.i(getClass().getSimpleName(), "onAttach");
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.i(getClass().getSimpleName(), "onCreate");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(getClass().getSimpleName(), "onActivityCreated");

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
    public void onDestroyView(){
        super.onDestroyView();
        Log.i(getClass().getSimpleName(), "onDestroyView");
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(getClass().getSimpleName(), "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(getClass().getSimpleName(), "onDetach");
    }

}
