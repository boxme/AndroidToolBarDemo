package com.desmond.androidtoolbardemo.googlePlayScrollingToolBar;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.desmond.androidtoolbardemo.GridRecyclerAdapter;
import com.desmond.androidtoolbardemo.R;
import com.desmond.androidtoolbardemo.ScrollTabHolderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerViewFragment extends ScrollTabHolderFragment {

    public static final String TAG = RecyclerViewFragment.class.getSimpleName();
    private static final int NO_SCROLL_X = 0;
    private static final String ARG_POSITION = "position";

    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutMgr;
    private int mPosition;

    public static Fragment newInstance(int position) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public RecyclerViewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        mPosition = getArguments().getInt(ARG_POSITION);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        mLayoutMgr = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutMgr);

        GridRecyclerAdapter gridRecyclerAdapter = new GridRecyclerAdapter();
        gridRecyclerAdapter.addItems(createItemList());
        mRecyclerView.setAdapter(gridRecyclerAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (mScrollTabHolder != null) {
                    mScrollTabHolder.onScrollStateChanged(recyclerView, newState);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (mScrollTabHolder != null) {
                    mScrollTabHolder.onRecyclerViewScroll(recyclerView, dx, dy, mPosition);
                }
            }
        });
    }

    private List<String> createItemList() {
        List<String> list = new ArrayList<>();
        for (int i = 1; i < 40; i++) {
            list.add("Item " + i);
        }
        return list;
    }

    @Override
    public void adjustScroll(int scrollHeight, int headerHeight) {
        if (mRecyclerView == null) return;

        mLayoutMgr.scrollToPositionWithOffset(0, -scrollHeight);
    }
}
