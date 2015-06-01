package com.desmond.androidtoolbardemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

import com.desmond.androidtoolbardemo.googlePlayScrollingToolBar.RecyclerViewFragment;

/**
 * Created by desmond on 31/5/15.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
    private int mNumFragments;

    public ViewPagerAdapter(FragmentManager fm, int numFragments) {
        super(fm);
        mNumFragments = numFragments;
        mScrollTabHolders = new SparseArrayCompat<>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = RecyclerViewFragment.newInstance(0);
                break;

            case 1:
                fragment = RecyclerViewFragment.newInstance(1);
                break;

            case 2:
                fragment = RecyclerViewFragment.newInstance(2);
                break;

            default:
                throw new IllegalArgumentException("Wrong page given " + position);
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Tab 1";

            case 1:
                return "Tab 2";

            case 2:
                return "Tab 3";

            default:
                throw new IllegalArgumentException("wrong position for the fragment in vehicle page");
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);

        mScrollTabHolders.put(position, (ScrollTabHolder) object);

        return object;
    }

    @Override
    public int getCount() {
        return mNumFragments;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
        return mScrollTabHolders;
    }

}
