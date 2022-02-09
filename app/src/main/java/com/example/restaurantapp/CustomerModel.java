package com.example.restaurantapp;

public class CustomerModel {
    private String Name;
    private String PhoneNumber;
    private int id;

    public CustomerModel(String name, String phoneNumber, int id) {
        Name = name;
        PhoneNumber = phoneNumber;
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "Name='" + Name + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", id=" + id +
                '}';
    }
}
