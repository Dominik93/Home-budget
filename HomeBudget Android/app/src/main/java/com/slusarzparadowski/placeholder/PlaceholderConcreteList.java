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
 * Created by Dominik on 2015-03-22.
 */
public class PlaceholderConcreteList extends PlaceholderList {

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderConcreteList newInstance(int sectionNumber, String type) {
        PlaceholderConcreteList fragment = new PlaceholderConcreteList();
        fragment.setType(type);
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderConcreteList() {
    }

}
