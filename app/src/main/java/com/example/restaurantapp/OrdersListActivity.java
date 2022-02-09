package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class OrdersListActivity extends AppCompatActivity {

    ListView listView;
    List<String> list;
    List<String>  listFinal;
    Button back;

    final static String KEY_ORDERS = "KEY_ORDERS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);
        listView = findViewById(R.id.orderListView);
        back = findViewById(R.id.backFromListView);
        list = new ArrayList<>();
        listFinal = new ArrayList<>();



        OrderDataBase orderDataBase = new OrderDataBase(OrdersListActivity.this);
        list = orderDataBase.getEveryOrder();
        for (int i = 0; i < list.size();i++){
            listFinal.add(list.get(i).split("@")[0] + list.get(i).split("@")[2]);
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.textviewlist, R.id.textInList, listFinal);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(OrdersListActivity.this, SingleOrderListActivity.class);

                String[] ids = list.get(position).split("@");
                i.putExtra(SingleOrderListActivity.KEY_ORDER, ids[0]);
                i.putExtra(SingleOrderListActivity.KEY_ID, ids[1].trim());
                i.putExtra(SingleOrderListActivity.KEY_NOTES, ids[3].trim());
                startActivity(i);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String[] ids = list.get(position).split("@");

                orderDataBase.deleteOrder(Integer.parseInt(ids[ids.length-3].trim()));
                Toast.makeText(getApplicationContext(), "Order Deleted", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(OrdersListActivity.this, OrdersListActivity.class);
                startActivity(i);
                return true;
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrdersListActivity.this, OrderActivity.class);
                startActivity(i);
            }
        });

    }
}