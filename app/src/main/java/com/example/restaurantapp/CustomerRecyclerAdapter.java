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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomerRecyclerAdapter extends RecyclerView.Adapter<CustomerRecyclerAdapter.RecyclerViewHolder> {

    Context context;
    ArrayList<CustomerModel> customerLists;
    private OnNoteListener onNoteListener;

    public CustomerRecyclerAdapter(Context context, ArrayList<CustomerModel> customerLists, OnNoteListener onNoteListener) {
        this.context = context;
        this.customerLists = customerLists;
        this.onNoteListener = onNoteListener;
    }


    @NonNull
    @Override
    public CustomerRecyclerAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.activity_customer_recycler_adapter, parent, false);
        RecyclerViewHolder recyclerViewHolder =new RecyclerViewHolder(view, onNoteListener);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerRecyclerAdapter.RecyclerViewHolder holder, int position) {
        String name = customerLists.get(position).getName();
        String num = customerLists.get(position).getPhoneNumber();
        holder.customerName.setText(name);
        holder.phoneNum.setText(num);

    }

    @Override
    public int getItemCount() {
        return customerLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener{
        TextView customerName, phoneNum;
        OnNoteListener onNoteListener;
        public RecyclerViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customerNameAdapter);
            phoneNum = itemView.findViewById(R.id.customerNumAdapter);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClicked(getAdapterPosition() );
        }

        @Override
        public boolean onLongClick(View v) {
             onNoteListener.onNoteLongClicked(getAdapterPosition());
            return true;
        }
    }

    public interface OnNoteListener{
        void onNoteLongClicked(int position);
        void onNoteClicked(int position);

    }
}