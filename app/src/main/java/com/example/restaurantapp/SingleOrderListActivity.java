package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SingleOrderListActivity extends AppCompatActivity {

    ListView listView;
    List<String> list;
    String customerName, price, orderPart;
    Button back, addFood;
    int idOrder;
    final static String KEY_ORDER = "KEY_ORDER";
    final static String KEY_ID = "KEY_ID";
    final static String KEY_NOTES = "KEY_NOTES";
    String[] notesList;
    String notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_order_list);
        listView = findViewById(R.id.singleOrderList);
        back = findViewById(R.id.backToOrdersPage);
        addFood = findViewById(R.id.addAnotherFood);
        list = new ArrayList<>();

        String orderDetails = getIntent().getStringExtra(KEY_ORDER);
        idOrder = Integer.parseInt(getIntent().getStringExtra(KEY_ID));
        notes = getIntent().getStringExtra(KEY_NOTES);
        customerName = orderDetails.split("-")[0];
        orderPart = orderDetails.split("-")[1];
        price = orderDetails.split("-")[2].split("@")[0].split(":")[1];

        String[] orderList = orderPart.split(",");
        notesList = notes.split(",");
        Toast.makeText(getApplicationContext(), notes,Toast.LENGTH_SHORT).show();
        for (int i = 0; i < orderList.length; i++){
            list.add(orderList[i].trim()+"__"+notesList[i].trim());

        }


        OrderDataBase orderDataBase = new OrderDataBase(SingleOrderListActivity.this);
        MenuDataBase menuDataBase = new MenuDataBase(SingleOrderListActivity.this);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.textviewlist, R.id.textInList, list);
        listView.setAdapter(adapter);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPrice = Integer.parseInt(menuDataBase.getMenuPrice(list.get((int)id)).toString());

                list.remove((int)id);
                String updatedOrder = "";
                for (int i = 0; i < list.size(); i++){
                    updatedOrder += list.get(i);
                    if (i < list.size()-1){
                        updatedOrder += " , ";
                    }

                }
                price = String.valueOf(Integer.parseInt(price.trim()) - itemPrice);
                orderDataBase.updateOrder(customerName, price,updatedOrder, idOrder);
                Toast.makeText(getApplicationContext(), "Item Deleted", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(SingleOrderListActivity.this, OrdersListActivity.class);
                startActivity(i);
                return true;
            }
        });

//        Toast.makeText(getApplicationContext(), notes, Toast.LENGTH_SHORT).show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SingleOrderListActivity.this, UpdateNote.class);
                i.putExtra(UpdateNote.KEY_ID,(int)id);
                i.putExtra("id", idOrder);

                Toast.makeText(getApplicationContext(), notes, Toast.LENGTH_SHORT).show();
                i.putExtra(UpdateNote.KEY_NOTE,notes);
                startActivity(i);

            }
        });
//

        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SingleOrderListActivity.this, AddExtra.class);
                i.putExtra(AddExtra.KEY_ORDERS, orderPart);
                i.putExtra("id", idOrder);
                i.putExtra(AddExtra.KEY_NAME, customerName);
                i.putExtra(AddExtra.KEY_PRICE, price);
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SingleOrderListActivity.this, OrdersListActivity.class);
                startActivity(i);
            }
        });
    }
}