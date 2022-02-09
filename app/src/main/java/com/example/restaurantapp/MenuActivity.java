package com.example.restaurantapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity implements CustomAdapter.OnNoteListener {

    Button addMenu, goHome;
    RecyclerView mainCourseList, appetizersList,dessertsList;

    ArrayList<MenuModel> mainList = new ArrayList<>();
    ArrayList<MenuModel> appetList = new ArrayList<>();
    ArrayList<MenuModel> dessertList = new ArrayList<>();

    final static String KEY_FOODNAME = "KEY_FOODNAME";
    final static String KEY_PRICE = "KEY_PRICE";
    final static String KEY_FOODTYPE = "KEY_FOODTYPE";


    MenuDataBase menuDataBase;
    List<MenuModel> everyDish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        addMenu = findViewById(R.id.id_add_menu);
        goHome = findViewById(R.id.id_home);

        //find the recyclerviews
        mainCourseList =findViewById(R.id.id_menu_list);
        appetizersList = findViewById(R.id.id_appetizer_rv);
        dessertsList = findViewById(R.id.id_desert_recycler);

        //initiliaze the arraylists to data from the database
        menuDataBase = new MenuDataBase(MenuActivity.this);
        everyDish = menuDataBase.getEveryDish();

        for (int i = 0; i < everyDish.size(); i++){
            if (everyDish.get(i).getType().equals("Main Dishes")){
                mainList.add(everyDish.get(i));
            }
            else if (everyDish.get(i).getType().equals("Appetizers")){
                appetList.add(everyDish.get(i));
            }
            else if (everyDish.get(i).getType().equals("Desserts")){
                dessertList.add(everyDish.get(i));
            }
        }



        //set adapter to the recyclerviews
        CustomAdapter adapterMain =new CustomAdapter(getApplicationContext(), mainList, "Main Dishes", this);
        CustomAdapter adapterAppetizer=new CustomAdapter(getApplicationContext(), appetList,  "Appetizers", this);
        CustomAdapter adapterDessert=new CustomAdapter(getApplicationContext(), dessertList, "Desserts", this);

        mainCourseList.setAdapter(adapterMain);
        mainCourseList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        appetizersList.setAdapter(adapterAppetizer);
        appetizersList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        dessertsList.setAdapter(adapterDessert);
        dessertsList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //clicking on the home button will finish the intent
        goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //add a divider to the recyclerview
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        mainCourseList.addItemDecoration(dividerItemDecoration);
        appetizersList.addItemDecoration(dividerItemDecoration);
        dessertsList.addItemDecoration(dividerItemDecoration);
        //define activityResultLauncher
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>(){
            @Override
            public void onActivityResult(ActivityResult result) {

                if (result.getResultCode() == RESULT_OK){
                    Intent i = result.getData();
                    String food_Name = i.getStringExtra(KEY_FOODNAME).trim();
                    String price = i.getStringExtra(KEY_PRICE).trim();
                    String food_type = i.getStringExtra(KEY_FOODTYPE).trim();




                    MenuModel menuModel = new MenuModel(food_Name, food_type, -1, price);
                    boolean b = menuDataBase.addDish(menuModel);


                    if (menuModel.getType().equals("Main Dishes")) {
                        mainList.add(menuModel);
                    } else if (menuModel.getType().equals("Appetizers")) {
                        appetList.add(menuModel);
                    } else if (menuModel.getType().equals("Desserts")) {
                        dessertList.add(menuModel);
                    }


                    }

                }
            }

        );


        addMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(MenuActivity.this, AddMenu.class);
                activityResultLauncher.launch(i);


            }
        });
    }

    @Override
    public void onNoteLongClicked(int position, String type) {

        if (type.equals("Main Dishes")){

            menuDataBase.deleteDish(mainList.get(position));
            mainList.remove(position);
            mainCourseList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }
        else if (type.equals("Appetizers") ){
            menuDataBase.deleteDish(appetList.get(position));
            appetList.remove(position);
            appetizersList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }
        else if (type.equals("Desserts")){
            menuDataBase.deleteDish(dessertList.get(position));
            dessertList.remove(position);
            dessertsList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }
    }

    public void onNoteClicked(int position, String type){

        Intent i = new Intent(MenuActivity.this, UpdateMenu.class);
        int id = -1;
       String name = "", price = "";
        if (type.equals("Main Dishes")){
            name = mainList.get(position).getName();
            price = mainList.get(position).getPrice();
            id = mainList.get(position).getId();

        }
        else if (type.equals("Appetizers") ){
            name = appetList.get(position).getName();
            price = appetList.get(position).getPrice();
            id = appetList.get(position).getId();

        }
        else if (type.equals("Desserts")) {
            name = dessertList.get(position).getName();
            price = dessertList.get(position).getPrice();
            id = dessertList.get(position).getId();


        }
        if (name != "" && price != ""){
            i.putExtra(UpdateMenu.KEY_NAME,name);
            i.putExtra(UpdateMenu.KEY_PRICE, price);
            i.putExtra(UpdateMenu.KEY_TYPE, type);
            i.putExtra(UpdateMenu.KEY_POS, id);

        }
        startActivity(i);



    }

}