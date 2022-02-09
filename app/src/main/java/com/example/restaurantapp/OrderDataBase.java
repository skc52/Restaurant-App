package com.example.restaurantapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderDataBase extends SQLiteOpenHelper {

    public static final String ORDER_TABLE = "ORDER_TABLE";
    public static final String ID = "ID";
    public static final String PRICE = "PRICE";
    public static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String ORDERS = "ORDERS";
    public static final String TIME = "TIME";
    public static final String SPECIALINSTRUCTIONS = "SPECIALINSTRUCTIONS";


    public OrderDataBase(@Nullable Context context) {
        super(context, "orders.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + ORDER_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CUSTOMER_NAME + " CLASS, "+ PRICE + " TEXT, " +ORDERS + " TEXT, "+ TIME+ " TEXT, "+SPECIALINSTRUCTIONS+" TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addOrder(OrderModel orderModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CUSTOMER_NAME, orderModel.getCustomer().getName());
        cv.put(PRICE, orderModel.getTotalPrice());
        cv.put(ORDERS, orderModel.toString());
        cv.put(SPECIALINSTRUCTIONS, orderModel.notesString());

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
        String formattedDate = df.format(c.getTime());
        cv.put(TIME, formattedDate);

        long insert = db.insert(ORDER_TABLE, null, cv);
        db.close();
        if (insert == -1){
            return false;
        }
        else
        {

            return true;
        }
    }
    public List<String> getEveryOrder(){
        String queryString = "SELECT * FROM " + ORDER_TABLE;
        List<String > ordersLIst = new ArrayList<>();
        String orderInfo= "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            //loop through the results and create new menu objects, Put these into the everyDIsh list
            do{
                int orderID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                String priceTag = cursor.getString(2);
                String order = cursor.getString(3);
                String timeOrder = cursor.getString(4);
                String notes = cursor.getString(5);

                orderInfo = "";
                orderInfo =  customerName +" - " + order +  " - Total : " + priceTag + " @ " + orderID+" @ "+timeOrder + " @ " + notes;
                ordersLIst.add(orderInfo);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ordersLIst;
    }

    public boolean deleteOrder(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + ORDER_TABLE + " WHERE " + ID +"="+ id;
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

    public boolean updateOrder(String name, String price, String order, int id){

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        cv.put(CUSTOMER_NAME, name);
        cv.put(PRICE, price);
        cv.put(ORDERS, order);
//
        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        int result = db.update(ORDER_TABLE, cv, ID +"=?", new String[]{String.valueOf(id)});

        db.close();

        return result > 0;
    }

    public boolean updateNote(int id, String[] notes){

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        String notestring = "";
        for (int i = 0; i < notes.length; i++){
            notestring +=  notes[i].trim();
            if (i < notes.length-1){
                notestring += " , ";
            }
        }
        cv.put(SPECIALINSTRUCTIONS, notestring);
//
        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        int result = db.update(ORDER_TABLE, cv, ID +"=?", new String[]{String.valueOf(id)});

        db.close();

        return result > 0;
    }
}
