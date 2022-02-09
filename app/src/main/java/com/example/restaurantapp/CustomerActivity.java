package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity implements CustomerRecyclerAdapter.OnNoteListener {

    EditText customerName, phoneNumber;
    Button addCustomer, goBack;
    RecyclerView customers;
    CustomerModel customerModel;
    CustomerDatabase customerDatabase;
    ArrayList<CustomerModel> customerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        customerName = findViewById(R.id.customerName);
        phoneNumber = findViewById(R.id.phoneNumber);
        goBack = findViewById(R.id.backButton);
        addCustomer = findViewById(R.id.addCustomer);
        customers = findViewById(R.id.customerList);
        customerDatabase = new CustomerDatabase(CustomerActivity.this);
        customerList = (ArrayList<CustomerModel>) customerDatabase.getEveryCustomer();

        CustomerRecyclerAdapter adapter = new CustomerRecyclerAdapter(getApplicationContext(), customerList, this);
        customers.setAdapter(adapter);
        customers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));





        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!customerName.getText().toString().isEmpty() && !phoneNumber.getText().toString().isEmpty()){
                    customerModel = new CustomerModel(customerName.getText().toString().trim(), phoneNumber.getText().toString().trim(),-1);
                    boolean b = customerDatabase.addCustomer(customerModel);
                    Toast.makeText(getApplicationContext(), "Customer Added!",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CustomerActivity.this, MainActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid Input",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    public void onNoteClicked(int position) {
        Intent i = new Intent(CustomerActivity.this, UpdateCustomer.class);
        String name = "", num = "";
        name = customerList.get(position).getName();
        num = customerList.get(position).getPhoneNumber();
        int id = customerList.get(position).getId();
        i.putExtra(UpdateCustomer.KEY_NAME, name);
        i.putExtra(UpdateCustomer.KEY_NUM, num);
        i.putExtra(UpdateCustomer.KEY_ID, id);
        startActivity(i);

    }

    @Override
    public void onNoteLongClicked(int position) {
        customerDatabase.deleteCustomer(customerList.get(position));
        customerList.remove(position);
        customers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Toast.makeText(getApplicationContext(), "Customer Deleted", Toast.LENGTH_SHORT).show();


    }
}