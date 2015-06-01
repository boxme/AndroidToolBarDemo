package com.desmond.androidtoolbardemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.desmond.androidtoolbardemo.googlePlayScrollingToolBar.GooglePlayToolBarActivity;
import com.desmond.androidtoolbardemo.hideToolbarOnScroll.HideOnScrollToolBarActivity;
import com.desmond.androidtoolbardemo.toolbarWithSpinner.ToolBarActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchToolbarActivity(View view) {
        Intent toolbarActivityIntent = new Intent(this, ToolBarActivity.class);
        startActivity(toolbarActivityIntent);
    }

    public void launchToolbarHideOnScrollActivity(View view) {
        Intent toolbarActivityIntent = new Intent(this, HideOnScrollToolBarActivity.class);
        startActivity(toolbarActivityIntent);
    }
    public void launchGooglePlayToolBarActivity(View view) {
        Intent intent = new Intent(this, GooglePlayToolBarActivity.class);
        startActivity(intent);
    }
}
