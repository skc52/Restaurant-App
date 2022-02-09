package com.example.restaurantapp;

public class MenuModel {

    private String name;
    private String type;
    private String price;
    private int id;

    public MenuModel(String name, String type, int id, String price) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MenuModel{" +
                "name='" + name + '\'' +
                "price = '" + price + '\''+
                ", type='" + type + '\'' +
                ", id=" + id +
                '}';
    }
}

