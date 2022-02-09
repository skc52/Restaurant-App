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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UpdateMenu extends AppCompatActivity {
    EditText editFoodName, editPrice;
    Spinner menuTypeSpinner;
    String menuTypeString;
    Button update;
    int id;
    //
    final static String KEY_NAME = "KEY_FOODNAME";
    final static String KEY_PRICE = "KEY_PRICE";
    final static String KEY_TYPE = "KEY_TYPE";
    final static String KEY_POS = "KEY_POS";


    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu);
        editFoodName = findViewById(R.id.editDIshName);
        editPrice = findViewById(R.id.editPrice);
        menuTypeSpinner = findViewById(R.id.menuTypeSpinnerIdUp);
        update = findViewById(R.id.update);

        //Spinner Drop down elements
        List<String> menuTypeList = new ArrayList<String>();
        menuTypeList.add("Main Dishes");
        menuTypeList.add("Appetizers");
        menuTypeList.add("Desserts");

        //Creating adapter for spinner
        MenuSpinnerAdapter menuSpinnerAdapter =new MenuSpinnerAdapter(this, R.layout.activity_menu_spinner_adapter, R.id.displayDishType, menuTypeList);

        //Dropdown layout style -list view with radio buttons
        menuSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //attaching spinnerAdapter to spinner
        menuTypeSpinner.setAdapter(menuSpinnerAdapter);
        //----------------------------

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String name = extras.getString(KEY_NAME);
            String price = extras.getString(KEY_PRICE);
            String type = extras.getString(KEY_TYPE);
            id= extras.getInt(KEY_POS);
            editFoodName.setText(name);
            editPrice.setText(price);
            if (type.equals("Main Dishes")){
                menuTypeSpinner.setSelection(0);
            }
            else if (type.equals("Appetizers")){
                menuTypeSpinner.setSelection(1);

            }
            else if (type.equals("Desserts")){
                menuTypeSpinner.setSelection(2);

            }

        }
        //----------------------------

        //menuOptionSpinner click listener
        menuTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                menuTypeString =parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                menuTypeString = null;
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodName =editFoodName.getText().toString().trim();
                String price = editPrice.getText().toString().trim();

                if (menuTypeString != null && foodName.isEmpty()==false && price.isEmpty() == false){


                    MenuDataBase menuDataBase = new MenuDataBase(UpdateMenu.this);

                    menuDataBase.updateDish(menuTypeString, foodName, price, id);
                    Toast.makeText(getApplicationContext(),  "Updated dish!", Toast.LENGTH_SHORT ).show();

                    Intent i = new Intent(UpdateMenu.this, MainActivity.class);
                    startActivity(i);
                    //finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Invalid Input",Toast.LENGTH_SHORT ).show();

                }
            }
        });


    }
}