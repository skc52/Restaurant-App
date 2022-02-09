package com.example.restaurantapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomerDatabase extends SQLiteOpenHelper {
    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String CUSTOMER_NUM = "CUSTOMER_NUM";
    public static final String ID = "ID";




    public CustomerDatabase(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CUSTOMER_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CUSTOMER_NAME + " TEXT, " + CUSTOMER_NUM + " TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addCustomer(CustomerModel customerModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CUSTOMER_NAME, customerModel.getName());
        cv.put(CUSTOMER_NUM, customerModel.getPhoneNumber());

        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        db.close();
        if (insert == -1){
            return false;
        }
        else
        {
            return true;
        }

    }

    public List<CustomerModel> getEveryCustomer(){
        List<CustomerModel> everyCustomer = new ArrayList<>();
        String queryString = "SELECT * FROM " + CUSTOMER_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            //loop through the results and create new menu objects, Put these into the everyDIsh list
            do{
                int menuID = cursor.getInt(0);
                String name = cursor.getString(1);
                String num = cursor.getString(2);

                CustomerModel customerModel = new CustomerModel(name,num, menuID);
                everyCustomer.add(customerModel);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return everyCustomer;
    }

    public boolean deleteCustomer(CustomerModel customerModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + CUSTOMER_TABLE + " WHERE " + ID +"="+  customerModel.getId();

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

    public boolean updateCustomer(String name, String num, int id){

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        cv.put(CUSTOMER_NAME, name);
        cv.put(CUSTOMER_NUM, num);

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        int result = db.update(CUSTOMER_TABLE, cv, ID +"=?", new String[]{String.valueOf(id)});

        db.close();
        return result > 0;
    }
}
