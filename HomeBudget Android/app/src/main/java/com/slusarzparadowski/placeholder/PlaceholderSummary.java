package com.slusarzparadowski.placeholder;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slusarzparadowski.homebudget.R;
import com.slusarzparadowski.model.Model;

/**
 * Created by Dominik on 2015-03-22.
 */
public class PlaceholderSummary extends Placeholder {

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderSummary newInstance(int sectionNumber) {
        PlaceholderSummary fragment = new PlaceholderSummary();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    public PlaceholderSummary() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_summary, container, false);
        Log.i(getClass().getSimpleName(), "onCreateView");

        tv1 = (TextView) rootView.findViewById(R.id.textViewSummaryIncome);
        tv1.setText(getString(R.string.summary_income)+ " " + String.valueOf(model.getIncomeSum()));

        tv2 = (TextView) rootView.findViewById(R.id.textViewSummaryOutcome);
        tv2.setText(getString(R.string.summary_outcome)+ " " + String.valueOf(model.getOutcomeSum()));

        tv3 = (TextView) rootView.findViewById(R.id.textViewSummarySummary);
        double r = model.getSummary();
        if(r < 0)
            tv3.setTextColor(Color.RED);
        else
            tv3.setTextColor(Color.GREEN);
        tv3.setText(getString(R.string.summary_summary)+ " " + String.valueOf(r));

        tv4 = (TextView) rootView.findViewById(R.id.textViewSummarySavings);
        tv4.setText(getString(R.string.summary_savings)+ " " + String.valueOf(model.getUser().getSavings()));

        return rootView;
    }

    @Override
    public void update(Model model) {
        Log.i(getClass().getSimpleName(), "update");
        tv1.setText(getString(R.string.summary_income)+ " " + String.valueOf(model.getIncomeSum()));

        tv2.setText(getString(R.string.summary_outcome)+ " " + String.valueOf(model.getOutcomeSum()));

        double r = model.getSummary();
        if(r < 0)
            tv3.setTextColor(Color.RED);
        else
            tv3.setTextColor(Color.GREEN);
        tv3.setText(getString(R.string.summary_summary)+ " " + String.valueOf(r));

        tv4.setText(getString(R.string.summary_savings)+ " " + String.valueOf(model.getUser().getSavings()));
    }

}