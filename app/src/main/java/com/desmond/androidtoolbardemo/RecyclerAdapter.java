package com.desmond.androidtoolbardemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by desmond on 24/5/15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mItemList;

    public RecyclerAdapter() {
        super();
        mItemList = new ArrayList<>();
    }

    public void addItems(List<String> list) {
        mItemList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_item, viewGroup, false);
        return new RecyclerItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
        holder.mItemTextView.setText(mItemList.get(i));
    }

    @Override
    public int getItemCount() {
        return mItemList == null? 0 : mItemList.size();
    }

    private static class RecyclerItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView mItemTextView;

        public RecyclerItemViewHolder(View itemView) {
            super(itemView);
            mItemTextView = (TextView) itemView.findViewById(R.id.itemTextView);
        }
    }
}
