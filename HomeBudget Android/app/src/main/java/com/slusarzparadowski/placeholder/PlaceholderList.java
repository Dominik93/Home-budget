package com.slusarzparadowski.placeholder;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.slusarzparadowski.adapter.ExpandableItemListAdapter;
import com.slusarzparadowski.homebudget.MainActivity;
import com.slusarzparadowski.homebudget.R;
import com.slusarzparadowski.model.Model;

/**
 * Created by Dominik on 2015-04-26.
 */
public abstract class PlaceholderList extends Placeholder {

    protected ExpandableListView expandableListView;
    protected String type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        Log.i(getClass().getSimpleName(), "onCreateView");
        this.expandableListView = (ExpandableListView)rootView.findViewById(R.id.expandableListView);
        this.expandableListView.setAdapter(new ExpandableItemListAdapter(rootView.getContext(), getActivity(), ((MainActivity)getActivity()).getModel(), type));
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(getClass().getSimpleName(), "onResume");
        expandableListView.setAdapter((new ExpandableItemListAdapter(getActivity().getApplicationContext(), getActivity(), ((MainActivity)getActivity()).getModel(), type)));
    }

    @Override
    public void update() {
        Log.i(getClass().getSimpleName(), "update");
        expandableListView.setAdapter((new ExpandableItemListAdapter(getActivity().getApplicationContext(), getActivity(), ((MainActivity)getActivity()).getModel(), type)));
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
