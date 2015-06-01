package com.desmond.androidtoolbardemo.googlePlayScrollingToolBar;

import android.os.Bundle;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ScrollView;

import com.desmond.androidtoolbardemo.R;
import com.desmond.androidtoolbardemo.ScrollTabHolder;
import com.desmond.androidtoolbardemo.Utils;
import com.desmond.androidtoolbardemo.ViewPagerAdapter;
import com.desmond.androidtoolbardemo.slidingTab.SlidingTabLayout;

public class GooglePlayToolBarActivity extends AppCompatActivity implements ScrollTabHolder {

    public static final String TAG = GooglePlayToolBarActivity.class.getSimpleName();

    private Toolbar mToolBar;
    private View mToolBarContainer;
    private View mParallaxView;

    private static final float HIDE_THRESHOLD = 10;
    private static final float SHOW_THRESHOLD = 70;
    private int mToolbarHeight;
    private int mToolbarContainerHeight;
    private int mToolBarOffset;
    private int mTotalScrolledDistance;
    private boolean mControlsVisible = true;

    private SlidingTabLayout mViewPagerTab;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_play_tool_bar);

        mToolBarContainer = findViewById(R.id.toolbar_container);
        mParallaxView = findViewById(R.id.colored_bg_view);

        setMinTranslation();
        setupToolbar();

        setupViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_google_play_tool_bar, menu);
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

    private void setMinTranslation() {
        mToolbarHeight = Utils.getToolbarHeight(this);
        mToolbarContainerHeight = getResources().getDimensionPixelSize(R.dimen.tabsHeight) +  mToolbarHeight;
    }

    private void setupToolbar() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
    }

    private void setupViewPager() {
        mViewPagerTab = (SlidingTabLayout) findViewById(R.id.viewpager_sliding_tab);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        if (mAdapter == null) {
            mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 3);
        }

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPagerTab.setOnPageChangeListener(getViewPagerPageChangeListener());
        mViewPagerTab.setViewPager(mViewPager);
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {}

    @Override
    public void onScrollStateChanged(View view, int scrollState) {
        if (view instanceof RecyclerView) {
            if (scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                if (mTotalScrolledDistance < mToolbarContainerHeight) {
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
                        if (mToolbarContainerHeight - mToolBarOffset > SHOW_THRESHOLD) {
                            showToolbar();
                        } else {
                            hideToolbar();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onListViewScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount, int pagePosition) {}

    @Override
    public void onScrollViewScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition) {}

    @Override
    public void onRecyclerViewScroll(RecyclerView view, int dx, int dy, int pagePosition) {
        if (mViewPager.getCurrentItem() == pagePosition) {
            clipToolBarOffset();
            scrollParallaxView(mTotalScrolledDistance);
            // Scroll down
            if (dy > 0) {
                hideToolbarBy(dy);
            } else {
                showToolbarBy(dy);
            }

            // offset is smaller than toolbar and scrolling down OR offset is more than 0 and scrolling up
            if ((mToolBarOffset < mToolbarContainerHeight && dy > 0) || (mToolBarOffset > 0 && dy < 0)) {
                mToolBarOffset += dy;
            }
            mTotalScrolledDistance += dy;
        }
    }

    private void clipToolBarOffset() {
        if(mToolBarOffset > mToolbarContainerHeight) {
            mToolBarOffset = mToolbarContainerHeight;
        } else if(mToolBarOffset < 0) {
            mToolBarOffset = 0;
        }
    }

    private void showToolbar() {
        mToolBarContainer.clearAnimation();
        ViewCompat.animate(mToolBarContainer)
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator(2));
        mControlsVisible = true;
    }

    private void hideToolbar() {
        mToolBarContainer.clearAnimation();
        ViewCompat.animate(mToolBarContainer)
                .translationY(-mViewPagerTab.getBottom())
                .setInterpolator(new AccelerateInterpolator(2));
        mControlsVisible = false;
    }

    private void scrollParallaxView(int scrollY) {
        mParallaxView.setTranslationY(-scrollY * 0.7f);
    }

    private void hideToolbarBy(int dy) {
        if (cannotHideMore(dy)) {
            mToolBarContainer.setTranslationY(-mViewPagerTab.getBottom());
        } else {
            mToolBarContainer.setTranslationY(mToolBarContainer.getTranslationY() - dy);
        }
    }

    private boolean cannotHideMore(int dy) {
        return Math.abs(mToolBarContainer.getTranslationY() - dy) > mViewPagerTab.getBottom();
    }

    private void showToolbarBy(int dy) {
        if (cannotShowMore(dy)) {
            mToolBarContainer.setTranslationY(0);
        } else {
            mToolBarContainer.setTranslationY(mToolBarContainer.getTranslationY() - dy);
        }
    }

    private boolean cannotShowMore(int dy) {
        return mToolBarContainer.getTranslationY() - dy > 0;
    }

    private ViewPager.OnPageChangeListener getViewPagerPageChangeListener () {
        ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int currentItem = mViewPager.getCurrentItem();
                if (positionOffsetPixels > 0) {
                    SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mAdapter.getScrollTabHolders();

                    ScrollTabHolder fragmentContent;
                    if (position < currentItem) {
                        // Revealed the previous page
                        fragmentContent = scrollTabHolders.valueAt(position);
                    } else {
                        // Revealed the next page
                        fragmentContent = scrollTabHolders.valueAt(position + 1);
                    }

                    fragmentContent.adjustScroll(mTotalScrolledDistance, mToolBarContainer.getHeight());
                }
            }

            @Override
            public void onPageSelected(int position) {
                SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mAdapter.getScrollTabHolders();

                if (scrollTabHolders == null || scrollTabHolders.size() != 3) {
                    return;
                }

                ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
                currentHolder.adjustScroll(mTotalScrolledDistance, mToolBarContainer.getHeight());
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        };

        return listener;
    }
}
