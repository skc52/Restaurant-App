package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.Inet4Address;
import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    ArrayList<String> customerNameList;
    ArrayList<String> orderNameList;
    Spinner spinnerCustomer, menuSpinner;
    EditText note;
    TextView displayOrders;
    Button addOrder, submitOrder, backbtn, viewList;
    ArrayList<String> orders;
    ArrayList<String> notes;

    String customerName = "";
    String menuName = "";
    String order = "";

    CustomerModel customer;
    MenuModel menu;
    ArrayList<MenuModel> orderList;

    ArrayList<OrderModel> orderModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        spinnerCustomer = findViewById(R.id.customerSpinner);
        menuSpinner = findViewById(R.id.menuSpinner);
        displayOrders = findViewById(R.id.showOrdersSoFar);
        customerNameList = new ArrayList<>();
        addOrder = findViewById(R.id.addOrder);
        submitOrder = findViewById(R.id.submitOrder);
        backbtn = findViewById(R.id.backToMain);
        viewList = findViewById(R.id.viewList);
        note = findViewById(R.id.noteEditTExt);
        note.setText("");
        CustomerDatabase customerDatabase = new CustomerDatabase(OrderActivity.this);
        ArrayList<CustomerModel> customerList = (ArrayList<CustomerModel>)customerDatabase.getEveryCustomer();



        for (int i = 0; i < customerList.size(); i++){
            customerNameList.add(customerList.get(i).getName().toString());
        }
        orderNameList = new ArrayList<>();
        MenuDataBase menuDataBase = new MenuDataBase(OrderActivity.this);
        ArrayList<MenuModel> menuAllList = (ArrayList<MenuModel>) menuDataBase.getEveryDish();

        for (int i = 0; i < menuAllList.size(); i++){
            orderNameList.add(menuAllList.get(i).getName().toString());
        }




        OrderSpinnerAdapter orderSpinnerAdapter = new OrderSpinnerAdapter(this,
                R.layout.activity_order_spinner_adapter, R.id.displayName, customerNameList);
        orderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCustomer.setAdapter(orderSpinnerAdapter);

        OrderSpinnerAdapter orderSpinnerAdapterMenu = new OrderSpinnerAdapter(this,
                R.layout.activity_order_spinner_adapter, R.id.displayName, orderNameList);
        orderSpinnerAdapterMenu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menuSpinner.setAdapter(orderSpinnerAdapterMenu);
        spinnerCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customerName =parent.getItemAtPosition(position).toString();
                customer = customerList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                customerName = null;
            }
        });
        orderList = new ArrayList<>();
        menuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                menuName =parent.getItemAtPosition(position).toString();
                menu = menuAllList.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                menuName = null;
            }
        });

        orders = new ArrayList<>();
        notes = new ArrayList<>();
        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!menuName.isEmpty() && !customerName.isEmpty()){
                    orders.add(menuName);
                    if (note.getText().toString().isEmpty()){
                        notes.add("none");
                    }
                    else{
                        notes.add(note.getText().toString());
                    }
                    order = "";
                    for (int i = 0; i< orders.size(); i++){
                        order = order+ orders.get(i).toString();
                        if (i < orders.size()-1){
                            order += " , ";
                        }
                    }
                    orderList.add(menu);
                    note.setText("");
                    displayOrders.setText(order);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                }

            }

        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        OrderDataBase orderDataBase = new OrderDataBase(OrderActivity.this);



        orderModelList = new ArrayList<>();
        submitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (orderList.size() != 0){
                    OrderModel finalOrder = new OrderModel(customer, orderList,-1, notes);
                    orderModelList.add(finalOrder);
                    boolean b = orderDataBase.addOrder(finalOrder);
                    if (b){
                        Toast.makeText(getApplicationContext(), "Order Submitted", Toast.LENGTH_SHORT).show();

                    }

                    Intent i = new Intent(OrderActivity.this, OrdersListActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Empty Order Cannot Be Submitted!", Toast.LENGTH_SHORT).show();

                }

            }
        });

        viewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderActivity.this, OrdersListActivity.class);
                startActivity(i);
            }
        });






    }
}