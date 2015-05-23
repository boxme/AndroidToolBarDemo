package com.desmond.androidtoolbardemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by desmond on 23/5/15.
 */
public class SpinnerAdapter extends BaseAdapter {

    private List<String> mItems = new ArrayList<>();

    public void clear() {
        mItems.clear();
    }

    public void addItem(String item) {
        mItems.add(item);
    }

    public void addItems(List<String> list) {
        mItems.addAll(list);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.toolbar_spinner_item_dropdown, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(getTitle(position));

        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.toolbar_spinner_item_actionbar, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(getTitle(position));

        return convertView;
    }

    private String getTitle(int position) {
        return position >= 0 && position < mItems.size() ? mItems.get(position) : "";
    }
}
