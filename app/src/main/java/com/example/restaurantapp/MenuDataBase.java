package com.example.restaurantapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MenuDataBase extends SQLiteOpenHelper {

    public static final String MENU_TABLE = "MENU_TABLE";
    public static final String ID = "ID";
    public static final String PRICE = "PRICE";
    public static final String MENU_NAME = "MENU_NAME";
    public static final String MENU_TYPE = "MENU_TYPE";

    public MenuDataBase(@Nullable Context context) {
        super(context, "menu.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + MENU_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + MENU_NAME + " TEXT, " + PRICE + " TEXT, " +MENU_TYPE + " TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addDish(MenuModel menuModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(MENU_NAME, menuModel.getName());
        cv.put(PRICE, menuModel.getPrice());
        cv.put(MENU_TYPE, menuModel.getType());

        long insert = db.insert(MENU_TABLE, null, cv);
        db.close();
        if (insert == -1){
            return false;
        }
        else
        {
            return true;
        }
    }

    public String getMenuPrice(String menuName){
        String priceOfMenu = "0";
        String queryString = "SELECT * FROM " + MENU_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            //loop through the results and create new menu objects, Put these into the everyDIsh list
            do{
                if (cursor.getString(1).trim().equals(menuName.trim())){
                    priceOfMenu = cursor.getString(2);
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
        return priceOfMenu;
    }

    public List<MenuModel> getEveryDish(){
        List<MenuModel> everyDish = new ArrayList<>();
        String queryString = "SELECT * FROM " + MENU_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            //loop through the results and create new menu objects, Put these into the everyDIsh list
            do{
                int menuID = cursor.getInt(0);
                String menuName = cursor.getString(1);
                String priceTag = cursor.getString(2);
                String menuType = cursor.getString(3);

                MenuModel menuModel = new MenuModel(menuName, menuType, menuID, priceTag);
                everyDish.add(menuModel);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return everyDish;
    }

    public boolean deleteDish(MenuModel menuModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + MENU_TABLE + " WHERE " + ID +"="+  menuModel.getId();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            db.close();
            return true;
        }
        else{
            db.close();
            return false;
        }

    }

    public boolean updateDish(String type, String name, String price, int id){

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        cv.put(MENU_NAME, name);
        cv.put(PRICE, price);
        cv.put(MENU_TYPE, type);
        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        int result = db.update(MENU_TABLE, cv, ID +"=?", new String[]{String.valueOf(id)});

        db.close();

        Log.d("TAG45", "updateDish: succes");
        return result > 0;
    }
}
