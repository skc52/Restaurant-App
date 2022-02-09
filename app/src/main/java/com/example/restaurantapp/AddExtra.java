package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddExtra extends AppCompatActivity {


    Button add, submit, goToListView;
    Spinner  menuSpinner;
    EditText note;
    TextView displayOrders;
    ArrayList<String> extraOrdersList;
    String menuName = "";
    MenuModel menu;
    ArrayList<String> orders;
    String order;
    String prevOrders, customerName, totalPrice;
    int idOrder, itemPrice;
    ArrayList<MenuModel> orderListExtra;

    final static String KEY_ORDERS = "KEY_ORDERS";
    final static String KEY_ID= "KEY_ID";
    final static String KEY_NAME = "KEY_NAME";
    final static String KEY_PRICE = "KEY_PRICE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_extra);
        add = findViewById(R.id.addOrderExtra);
        submit = findViewById(R.id.submitOrderExtra);
        goToListView = findViewById(R.id.goToList);
        menuSpinner = findViewById(R.id.menuSpinnerExtra);
        displayOrders = findViewById(R.id.showOrdersSoFarExtra);


        prevOrders = getIntent().getStringExtra(KEY_ORDERS);
        customerName = getIntent().getStringExtra(KEY_NAME);
        totalPrice = getIntent().getStringExtra(KEY_PRICE);
        idOrder = getIntent().getIntExtra("id", 0);
//        display menu items in spinner----------
        MenuDataBase menuDataBase = new MenuDataBase(AddExtra.this);
        ArrayList<MenuModel> menuAllList = (ArrayList<MenuModel>) menuDataBase.getEveryDish();
        extraOrdersList = new ArrayList<>();
        for (int i = 0; i < menuAllList.size(); i++){
            extraOrdersList.add(menuAllList.get(i).getName().toString());
        }

        OrderSpinnerAdapter orderSpinnerAdapterMenu = new OrderSpinnerAdapter(this,
                R.layout.activity_order_spinner_adapter, R.id.displayName, extraOrdersList);
        orderSpinnerAdapterMenu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menuSpinner.setAdapter(orderSpinnerAdapterMenu);


//        //---------------------------------
//
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
        orderListExtra = new ArrayList<>();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orders.add(menuName);
                order = "";
                for (int i = 0; i< orders.size(); i++){
                    order = order+ orders.get(i).toString();
                    if (i < orders.size()-1){
                        order += " , ";
                    }
                }
                itemPrice = Integer.parseInt(menuDataBase.getMenuPrice(menuName.toString()));
                totalPrice = String.valueOf(Integer.parseInt(totalPrice.trim()) + itemPrice);

                orderListExtra.add(menu);

                displayOrders.setText(order);
                order = ", " +order;
            }

        });

        OrderDataBase orderDataBase = new OrderDataBase(AddExtra.this);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prevOrders += order;

                orderDataBase.updateOrder(customerName, totalPrice, prevOrders, idOrder);
                Toast.makeText(getApplicationContext(), "Item Added", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(AddExtra.this, OrdersListActivity.class);
                startActivity(i);
            }
        });

        goToListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddExtra.this, OrdersListActivity.class);
                startActivity(i);
            }
        });


    }
}