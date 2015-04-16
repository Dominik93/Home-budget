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
 * Created by Dominik on 2015-03-27.
 */
public class PlaceholderOutcome extends Placeholder {

    private ExpandableListView expandableListView;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderOutcome newInstance(int sectionNumber) {
        PlaceholderOutcome fragment = new PlaceholderOutcome();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderOutcome() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        Log.i(getClass().getSimpleName(), "onCreateView");
        expandableListView = (ExpandableListView)rootView.findViewById(R.id.expandableListView);
        expandableListView.setAdapter(new ExpandableItemListAdapter(rootView.getContext(), getActivity(), ((MainActivity)getActivity()).getModel().getOutcome(), "OUTCOME"));
        return rootView;
    }

    @Override
    public void update(Model model) {
        Log.i(getClass().getSimpleName(), "update");
        expandableListView.setAdapter((new ExpandableItemListAdapter(getActivity().getApplicationContext(), getActivity(), model.getOutcome(), "OUTCOME")));
    }
}
