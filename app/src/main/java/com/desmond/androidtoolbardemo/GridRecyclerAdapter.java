package com.desmond.androidtoolbardemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by desmond on 31/5/15.
 */
public class GridRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG  = GridRecyclerAdapter.class.getSimpleName();
    private List<String> mItemList;

    public GridRecyclerAdapter() {
        super();
        mItemList = new ArrayList<>();
    }

    public void addItems(List<String> list) {
        mItemList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_item, parent, false);
        return new RecyclerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
        holder.mItemTextView.setText(mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    private static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView mItemTextView;

        public RecyclerItemViewHolder(View itemView) {
            super(itemView);
            mItemTextView = (TextView) itemView.findViewById(R.id.itemTextView);
        }
    }
}
