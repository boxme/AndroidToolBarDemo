package com.desmond.androidtoolbardemo.toolbarWithSpinner;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Spinner;

import com.desmond.androidtoolbardemo.R;
import com.desmond.androidtoolbardemo.RecyclerAdapter;
import com.desmond.androidtoolbardemo.SpinnerAdapter;

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

    private void setupRecyclerView() {
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
        recyclerAdapter.addItems(createItemList());
        recyclerView.setAdapter(recyclerAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private static final int HIDE_THRESHOLD = 20;
            private int mScrolledDist = 0;
            private boolean mControlsVisible = true;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisibleItem =
                        ((LinearLayoutManager) recyclerView.getLayoutManager())
                                .findFirstVisibleItemPosition();

                // show views if first item is first visible position and views are hidden
                if (firstVisibleItem == 0) {
                    if (!mControlsVisible) {
                        onShow();
                    }
                } else {
                    if (mScrolledDist > HIDE_THRESHOLD && mControlsVisible) {
                        onHide();
                        mScrolledDist = 0;
                    } else if (mScrolledDist < -HIDE_THRESHOLD && !mControlsVisible) {
                        onShow();
                        mScrolledDist = 0;
                    }
                }

                // Scrolling down and views are visible OR Scrolling up and views are hidden
                if ((mControlsVisible && dy > 0) || (!mControlsVisible && dy < 0)) {
                    mScrolledDist += dy;
                }
            }

            private void onHide() {
                hideViews();
                mControlsVisible = false;
            }

            private void onShow() {
                showViews();
                mControlsVisible = true;
            }
        });
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

    private void hideViews() {
        ViewCompat.animate(mToolBar).translationY(-mToolBar.getHeight()).setInterpolator(new AccelerateInterpolator(2));

        // Take margins into account when we are hiding views, otherwise FAB would’t fully hide.
//        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFabButton.getLayoutParams();
//        int fabBottomMargin = lp.bottomMargin;
//        mFabButton.animate().translationY(mFabButton.getHeight()+fabBottomMargin)
//                .setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        ViewCompat.animate(mToolBar).translationY(0).setInterpolator(new DecelerateInterpolator(2));
//        ViewCompat.animate(mFabButton).translationY(0).setInterpolator(new DecelerateInterpolator(2));
    }
}
