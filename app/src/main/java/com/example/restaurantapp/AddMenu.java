package com.example.restaurantapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddMenu extends AppCompatActivity {
    EditText addFoodName, addPrice;
    Spinner menuTypeSpinner;
    String menuTypeString;
    Button submitAdd;



    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        addFoodName = findViewById(R.id.addDishName);
        addPrice = findViewById(R.id.addPrice);
        menuTypeSpinner = findViewById(R.id.menuTypeSpinnerId);
        submitAdd = findViewById(R.id.submit);


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

        submitAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodName =addFoodName.getText().toString().trim();
                String price = addPrice.getText().toString().trim();



                if (menuTypeString != null && foodName.isEmpty()==false && price.isEmpty() == false){
                    Intent i = new Intent(AddMenu.this, MenuActivity.class);

                    i.putExtra(MenuActivity.KEY_FOODNAME, foodName);
                    i.putExtra(MenuActivity.KEY_PRICE, price);
                    i.putExtra(MenuActivity.KEY_FOODTYPE, menuTypeString);
                    setResult(RESULT_OK, i);
                    Toast.makeText(getApplicationContext(), "Dish Added", Toast.LENGTH_SHORT).show();

                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Invalid Input",Toast.LENGTH_SHORT ).show();

                }
            }
        });



    }
}