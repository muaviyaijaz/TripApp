package com.example.tripapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Upcoming_adapter extends ArrayAdapter<String> {

    private ArrayList<String> upcoming_names = new ArrayList<>();
    Context context;

    public Upcoming_adapter(@NonNull Activity context, ArrayList<String> names) {
        super(context, R.layout.ucoming_list_item,names);
        this.context=context;
        this.upcoming_names=names;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        String person_name = getItem(position);

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ucoming_list_item,parent,false);
        }

        //listview wali line

        TextView text = (TextView) convertView.findViewById(R.id.upcoming_city_name);
        text.setText(person_name);

        return convertView;
    }
}