package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class OrderSpinnerAdapter extends ArrayAdapter<String> {

    Context mainContext;
    List<String> customerList;
    int layout;
    LayoutInflater inflater;

    public OrderSpinnerAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<String> objects) {
        super(context, resource, textViewResourceId, objects);
        mainContext = context;
        customerList=objects;
        layout=resource;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v =inflater.inflate(layout, null);
        TextView textView = v.findViewById(R.id.displayName);
        textView.setText(customerList.get(position));
        return v;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);

    }

}