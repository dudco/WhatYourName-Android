package com.example.dudco.whatsyourname;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dudco on 2017. 1. 3..
 */
public class CustomGridAdapter extends BaseAdapter {
    List<Integer> colors = new ArrayList<>();

    public CustomGridAdapter(List<Integer> colors) {
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return colors.size();
    }

    @Override
    public Object getItem(int position) {
        return colors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, null);
        ImageView fab = (ImageView) view.findViewById(R.id.fab_item);

//        fab.setBackgroundTintList((ColorStateList.valueOf(parent.getResources().getColor(colors.get(position), parent.getContext().getTheme()))));
        fab.setBackgroundColor(parent.getResources().getColor(colors.get(position), parent.getContext().getTheme()));
//        parent.bringToFront();
//        convertView.bringToFront();
        return view;
    }
}
