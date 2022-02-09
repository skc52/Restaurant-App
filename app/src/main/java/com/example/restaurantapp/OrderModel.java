package com.example.restaurantapp;

import java.util.ArrayList;

public class OrderModel {
    private CustomerModel customer;
    private ArrayList<MenuModel> orders;
    private int totalPrice = 0;
    private int id;
    private ArrayList<String> notes;

    public int getId() {
        return id;
    }



    public OrderModel(CustomerModel customer, ArrayList<MenuModel> orders, int id, ArrayList<String> notes) {
        this.customer = customer;
        this.orders = orders;
        this.id = id;
        this.notes  = notes;
        findTotalPrice();
    }


    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public ArrayList<MenuModel> getOrders() {
        return orders;
    }
    public ArrayList<String> getNotes() {
        return notes;
    }
    public void setOrders(ArrayList<MenuModel> orders) {
        this.orders = orders;
    }
    public void addOrder(MenuModel order){
        orders.add(order);
    }

    public void findTotalPrice(){
        for (int i = 0; i < orders.size(); i++){
            totalPrice += Integer.parseInt(orders.get(i).getPrice());

        }
    }
    public int getTotalPrice(){
        return totalPrice;
    }


    @Override
    public String toString() {
        String ordersString = "";
        for (int i = 0; i < orders.size(); i++){
            ordersString += orders.get(i).getName();
            if (i < orders.size()-1){
                ordersString += " , ";
            }
        }
        return ordersString;
    }

    public String notesString() {
        String notestring = "";
        for (int i = 0; i < notes.size(); i++){
            notestring +=  notes.get(i).trim();
            if (i < notes.size()-1){
                notestring += " , ";
            }
        }
        return notestring;
    }
}
