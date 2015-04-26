package com.slusarzparadowski.homebudget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.Locale;

import com.slusarzparadowski.model.Model;
import com.slusarzparadowski.placeholder.PlaceholderConcreteList;
import com.slusarzparadowski.placeholder.PlaceholderSummary;


public class MainActivity extends MyActivity implements ActionBar.TabListener {

    private Model model;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    void initElements() {

    }

    @Override
    void initListeners() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(getClass().getSimpleName(), "onCreate MainActivity created");
        if (savedInstanceState != null){
            this.model = new Model(savedInstanceState);
        }
        else{
            this.model = new Model(getIntent().getExtras());
        }

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        Log.i(getClass().getSimpleName(), "onCreate adding tabs");
        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
        Log.i(getClass().getSimpleName(), "onCreate setCurrentItem(1)");
        mViewPager.setCurrentItem(1);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.i(getClass().getSimpleName(), "onSaveInstanceState save model");
        super.onSaveInstanceState(model.addToBundle(savedInstanceState));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(getClass().getSimpleName(), "onRestoreInstanceState load model");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.i(getClass().getSimpleName(), "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_version) {
            Log.i(getClass().getSimpleName(), "onOptionsItemSelected versionActivity");
            Intent intent = new Intent(this, VersionActivity.class);
            intent.putExtras(model.saveToBundle());
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_settings) {
            Log.i(getClass().getSimpleName(), "onOptionsItemSelected settingsActivity");
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtras(model.saveToBundle());
            startActivityForResult(intent, 1);
            return true;
        }
        else if (id == R.id.action_synchronization) {
            Log.i(getClass().getSimpleName(), "onOptionsItemSelected synchronizationActivity");
            Intent intent = new Intent(this, SyncActivity.class);
            intent.putExtras(model.saveToBundle());
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Log.i(getClass().getSimpleName(), "onActivityResult "+ RESULT_OK);
                this.model = new Model(data.getExtras());
                this.model.notification();
            }
            else if (resultCode == RESULT_CANCELED) {
                Log.i(getClass().getSimpleName(), "onActivityResult "+ RESULT_CANCELED);

            }
        }
        else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                Log.i(getClass().getSimpleName(), "onActivityResult "+ RESULT_OK);
                this.model = new Model(data.getExtras());
                this.model.notification();
            }
            else if (resultCode == RESULT_CANCELED) {
                Log.i(getClass().getSimpleName(), "onActivityResult "+ RESULT_CANCELED);

            }
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        Log.i(getClass().getSimpleName(), "onTabSelected "+ tab.getTag());
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        Log.i(getClass().getSimpleName(), "onTabUnselected "+ tab.getTag());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        Log.i(getClass().getSimpleName(), "onTabReselected "+ tab.getTag());
    }

    public void setModel(Model model){
        this.model = model;
        this.model.notification();
    }

    public Model getModel(){
        return this.model;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle;
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch(position){
                case 0:
                    PlaceholderConcreteList placeholderIncome = PlaceholderConcreteList.newInstance(position + 1, "INCOME");
                    model.attachPlaceholder(placeholderIncome);
                    return placeholderIncome;
                case 1:
                    PlaceholderSummary placeholderSummary = PlaceholderSummary.newInstance(position + 1);
                    model.attachPlaceholder(placeholderSummary);
                    return placeholderSummary;
                case 2:
                    PlaceholderConcreteList placeholderOutcome = PlaceholderConcreteList.newInstance(position + 1, "OUTCOME");
                    model.attachPlaceholder(placeholderOutcome);
                    return placeholderOutcome;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section1).toUpperCase(l);
            }
            return null;
        }
    }

}
