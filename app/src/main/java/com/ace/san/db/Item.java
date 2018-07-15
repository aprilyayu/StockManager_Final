package com.ace.san.db;

import com.google.firebase.database.IgnoreExtraProperties;

//name, category, status, place, quantity diambil dari pemodelan database
//public artinya semua yang ada di class Item dapat diakses secara publik atau umum
public class Item {
    private String name;
    private String category;
    private String status;
    private String place;
    private String quantity;

    public Item(){

    }

    //Konstraktor Item diambil dari Class Item
    public Item(String name, String status, String place, String category, String quantity){
        this.name = name;
        this.category = category;
        this.status = status;
        this.place = place;
        this.quantity = quantity;

    }

    //Getter diambil dari Konstaktor Item
    //digunakan untuk mengambil String name
    public String getName() {
        return name;
    }

    //digunakan untuk mengambil String category
    public String getCategory() {
        return category;
    }

    //digunakan untuk mengambil String status
    public String getStatus() {
        return status;
    }

    //digunakan untuk mengambil String place
    public String getPlace() {
        return place;
    }

    //digunakan untuk mengambil String quantity
    public String getQuantity() {
        return quantity; }
}
