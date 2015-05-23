package com.desmond.androidtoolbardemo;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;


public class ToolBarActivity extends AppCompatActivity {

    public static final String TAG = ToolBarActivity.class.getSimpleName();

    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        setupToolbarSpinner();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        /*
         * With a toolbar, we have to set the title text after
         * onCreate(), or the default label will overwrite our
         * settings.
         */
        //Set the title text
        mToolBar.setTitle("Toolbar");
        //Set the subtitle text
        mToolBar.setSubtitle("Toolbar with Spinner");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tool_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupToolbarSpinner() {
        View spinnerContainer = LayoutInflater.from(this)
                .inflate(R.layout.toolbar_spinner, mToolBar, false);

        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mToolBar.addView(spinnerContainer, lp);

        SpinnerAdapter spinnerAdapter = new SpinnerAdapter();
        spinnerAdapter.addItems(getSpinnerData());

        Spinner spinner = (Spinner) spinnerContainer.findViewById(R.id.toolbar_spinner);
        spinner.setAdapter(spinnerAdapter);
    }

    private List<String> getSpinnerData() {
        List<String> list = new ArrayList<>();
        list.add("Test 1");
        list.add("Test 2");
        list.add("Test 3");
        list.add("Test 4");
        list.add("Test 5");
        list.add("Test 6");
        list.add("Test 7");
        list.add("Test 8");
        list.add("Test 9");
        return list;
    }
}
