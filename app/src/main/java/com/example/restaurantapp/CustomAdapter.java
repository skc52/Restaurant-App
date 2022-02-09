package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.RecyclerViewHolder> {
    Context context;
    ArrayList<MenuModel> arrayList;
    String type;
    private OnNoteListener onNoteListener;

    public CustomAdapter(Context context, ArrayList<MenuModel> arrayList, String type, OnNoteListener onNoteListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.onNoteListener = onNoteListener;
        this.type = type;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_custom_adapter, parent, false);
        RecyclerViewHolder recyclerViewHolder =new RecyclerViewHolder(view, onNoteListener);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String dish_Name =arrayList.get(holder.getAdapterPosition()).getName();
        String price_Tag = arrayList.get(holder.getAdapterPosition()).getPrice();
        holder.dishName.setText(dish_Name);
        holder.price.setText(price_Tag);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onNoteListener.onNoteClicked(position,type);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        TextView dishName, price;
        OnNoteListener onNoteListener;
        public RecyclerViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            dishName =itemView.findViewById(R.id.dishName);
            price = itemView.findViewById(R.id.price);
            this.onNoteListener = onNoteListener;
            itemView.setOnLongClickListener(this);

        }


        @Override
        public boolean onLongClick(View v) {

            onNoteListener.onNoteLongClicked(getAdapterPosition(), type);
            Toast.makeText(context,  "Dish deleted", Toast.LENGTH_SHORT ).show();

            return true;

        }

//        @Override
//        public void onClick(View v) {
//
//            onNoteListener.onNoteClicked(getAdapterPosition(),type);
//
//        }
    }

    public interface OnNoteListener{
        void onNoteLongClicked(int position, String type);
        void onNoteClicked(int position, String type);

    }
}