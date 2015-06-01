package com.desmond.androidtoolbardemo.hideToolbarOnScroll;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.desmond.androidtoolbardemo.R;
import com.desmond.androidtoolbardemo.RecyclerAdapter;
import com.desmond.androidtoolbardemo.Utils;

import java.util.ArrayList;
import java.util.List;


public class HideOnScrollToolBarActivity extends AppCompatActivity {

    public static final String TAG = HideOnScrollToolBarActivity.class.getSimpleName();

    private Toolbar mToolBar;
    private View mToolBarContainer;
    private int mToolBarOffset = 0;
    private int mToolBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_on_scroll_tool_bar);

        mToolBarHeight = Utils.getToolbarHeight(this);

        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        mToolBarContainer = findViewById(R.id.toolbar_container);

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

            private static final float HIDE_THRESHOLD = 10;
            private static final float SHOW_THRESHOLD = 70;

            private boolean mControlsVisible = true;
            private int mTotalScrolledDistance;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                // No longer scrolling
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mTotalScrolledDistance < mToolBarHeight) {
                        showToolbar();
                    } else {
                        // Previously visible
                        if (mControlsVisible) {
                            if (mToolBarOffset > HIDE_THRESHOLD) {
                                hideToolbar();
                            } else {
                                showToolbar();
                            }
                        } else {
                            if (mToolBarHeight - mToolBarOffset > SHOW_THRESHOLD) {
                                showToolbar();
                            } else {
                                hideToolbar();
                            }
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                clipToolBarOffset();
                onMoved(mToolBarOffset);

                // offset is smaller than toolbar and scrolling down OR offset is more than 0 and scrolling up
                if ((mToolBarOffset < mToolBarHeight && dy > 0) || (mToolBarOffset > 0 && dy < 0)) {
                    mToolBarOffset += dy;
                }

                mTotalScrolledDistance += dy;
            }

            private void clipToolBarOffset() {
                if(mToolBarOffset > mToolBarHeight) {
                    mToolBarOffset = mToolBarHeight;
                } else if(mToolBarOffset < 0) {
                    mToolBarOffset = 0;
                }
            }

            private void onMoved(int distance) {
                mToolBarContainer.setTranslationY(-distance);
            }

            private void showToolbar() {
                ViewCompat.animate(mToolBarContainer)
                        .translationY(0)
                        .setInterpolator(new DecelerateInterpolator(2));
                mControlsVisible = true;
            }

            private void hideToolbar() {
                ViewCompat.animate(mToolBarContainer)
                        .translationY(-mToolBarHeight)
                        .setInterpolator(new AccelerateInterpolator(2));
                mControlsVisible = false;
            }
        });
    }

    private List<String> createItemList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("Item " + i);
        }
        return list;
    }
}
