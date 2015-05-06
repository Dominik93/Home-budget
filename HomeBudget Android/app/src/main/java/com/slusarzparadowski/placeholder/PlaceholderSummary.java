package com.slusarzparadowski.placeholder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.slusarzparadowski.adapter.ExpandableItemListAdapter;
import com.slusarzparadowski.dialog.InternetAccessDialog;
import com.slusarzparadowski.dialog.NotificationDialog;
import com.slusarzparadowski.homebudget.GraphActivity;
import com.slusarzparadowski.homebudget.MainActivity;
import com.slusarzparadowski.homebudget.R;
import com.slusarzparadowski.model.Model;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Dominik on 2015-03-22.
 */
public class PlaceholderSummary extends Placeholder {

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private Button b1;

    ProgressDialog progressDialog;

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
        b1 = (Button)rootView.findViewById(R.id.buttonGraph);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CheckInternetAccess().execute();
            }
        });
        tv1 = (TextView) rootView.findViewById(R.id.textViewSummaryIncome);
        tv1.setText(getString(R.string.summary_income)+ " " + String.valueOf(((MainActivity)getActivity()).getModel().getIncomeSum()));

        tv2 = (TextView) rootView.findViewById(R.id.textViewSummaryOutcome);
        tv2.setText(getString(R.string.summary_outcome)+ " " + String.valueOf(((MainActivity)getActivity()).getModel().getOutcomeSum()));

        tv3 = (TextView) rootView.findViewById(R.id.textViewSummarySummary);
        double r = ((MainActivity)getActivity()).getModel().getSummary();
        if(r < 0)
            tv3.setTextColor(Color.RED);
        else
            tv3.setTextColor(Color.GREEN);
        tv3.setText(getString(R.string.summary_summary)+ " " + String.valueOf(r));

        tv4 = (TextView) rootView.findViewById(R.id.textViewSummarySavings);
        tv4.setText(getString(R.string.summary_savings)+ " " + String.valueOf(((MainActivity)getActivity()).getModel().getUser().getSavings()));
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(getClass().getSimpleName(), "onResume");
        tv1.setText(getString(R.string.summary_income)+ " " + String.valueOf(((MainActivity)getActivity()).getModel().getIncomeSum()));

        tv2.setText(getString(R.string.summary_outcome)+ " " + String.valueOf(((MainActivity)getActivity()).getModel().getOutcomeSum()));

        double r = ((MainActivity)getActivity()).getModel().getSummary();
        if(r < 0)
            tv3.setTextColor(Color.RED);
        else
            tv3.setTextColor(Color.GREEN);
        tv3.setText(getString(R.string.summary_summary)+ " " + String.valueOf(r));

        tv4.setText(getString(R.string.summary_savings)+ " " + String.valueOf(((MainActivity)getActivity()).getModel().getUser().getSavings()));
    }

    @Override
    public void update() {
        Log.i(getClass().getSimpleName(), "update");
        tv1.setText(getString(R.string.summary_income)+ " " + String.valueOf(((MainActivity)getActivity()).getModel().getIncomeSum()));

        tv2.setText(getString(R.string.summary_outcome)+ " " + String.valueOf(((MainActivity)getActivity()).getModel().getOutcomeSum()));

        double r = ((MainActivity)getActivity()).getModel().getSummary();
        if(r < 0)
            tv3.setTextColor(Color.RED);
        else
            tv3.setTextColor(Color.GREEN);
        tv3.setText(getString(R.string.summary_summary)+ " " + String.valueOf(r));

        tv4.setText(getString(R.string.summary_savings)+ " " + String.valueOf(((MainActivity)getActivity()).getModel().getUser().getSavings()));
    }

    class CheckInternetAccess extends AsyncTask<Void, String, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(getClass().getSimpleName(), "onPreExecute");
            progressDialog = new ProgressDialog(getActivity());
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
            progressDialog.dismiss();
            if(!return_value){
                Log.i(getClass().getSimpleName(), "onPostExecute No internet access turn on wifi or 3G");
                new NotificationDialog((MainActivity)getActivity(), R.layout.notification_dialog, "Work ony with internet access").buildDialog().show();
            }
            else{
                Intent intent = new Intent(getActivity(), GraphActivity.class);
                intent.putExtras(((MainActivity)getActivity()).getModel().saveToBundle());
                startActivity(intent);
            }

        }

        private boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
}