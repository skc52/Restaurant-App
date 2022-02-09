package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateCustomer extends AppCompatActivity {
    EditText updateName, updateNum;
    Button Update, Back;

    final static String KEY_NAME = "KEY_NAME";
    final static String KEY_NUM = "KEY_NUM";
    final static String KEY_ID = "KEY_ID";

    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_customer);
        updateName = findViewById(R.id.updateName);
        updateNum = findViewById(R.id.updateNumber);
        Update = findViewById(R.id.updateCustomerBtn);
        Back = findViewById(R.id.backBtn);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            updateName.setText(extras.getString(KEY_NAME).toString());
            updateNum.setText(extras.getString(KEY_NUM).toString());
            id = extras.getInt(KEY_ID);

        }
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerDatabase customerDatabase = new CustomerDatabase(UpdateCustomer.this);
                customerDatabase.updateCustomer(updateName.getText().toString(), updateNum.getText().toString(), id);
                Toast.makeText(getApplicationContext(), "Customer Updated", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UpdateCustomer.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

}