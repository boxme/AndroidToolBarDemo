package com.desmond.androidtoolbardemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class HideOnScrollToolBarActivity extends AppCompatActivity {

    public static final String TAG = HideOnScrollToolBarActivity.class.getSimpleName();

    private Toolbar mToolBar;
    private int mToolBarOffset = 0;
    private int mToolBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_on_scroll_tool_bar);

        mToolBarHeight = Utils.getToolbarHeight(this);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        setupRecyclerView();
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
        mToolBar.setSubtitle("Toolbar Hide On Scroll");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hide_on_scroll_tool_bar, menu);
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

    private void setupRecyclerView() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
        recyclerAdapter.addItems(createItemList());
        recyclerView.setAdapter(recyclerAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                clipToolBarOffset();
                onMoved(mToolBarOffset);

                // offset is smaller than toolbar and scrolling down OR offset is more than 0 and scrolling up
                if ((mToolBarOffset < mToolBarHeight && dy > 0) || (mToolBarOffset > 0 && dy < 0)) {
                    mToolBarOffset += dy;
                }
            }

            private void clipToolBarOffset() {
                if(mToolBarOffset > mToolBarHeight) {
                    mToolBarOffset = mToolBarHeight;
                } else if(mToolBarOffset < 0) {
                    mToolBarOffset = 0;
                }
            }

            private void onMoved(int distance) {
                mToolBar.setTranslationY(-distance);
            }
        });
    }

    private List<String> createItemList() {
        List<String> list = new ArrayList<>();
        list.add("Item 1");
        list.add("Item 2");
        list.add("Item 3");
        list.add("Item 4");
        list.add("Item 5");
        list.add("Item 6");
        list.add("Item 7");
        list.add("Item 8");
        list.add("Item 9");
        list.add("Item 10");
        list.add("Item 11");
        list.add("Item 12");
        list.add("Item 13");
        list.add("Item 14");
        list.add("Item 15");
        list.add("Item 16");
        list.add("Item 17");
        list.add("Item 18");
        list.add("Item 19");
        return list;
    }
}
