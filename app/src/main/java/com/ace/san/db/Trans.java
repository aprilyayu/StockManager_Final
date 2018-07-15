package com.ace.san.db;

//variabel name, quantity, from, to diambil dari pemodelan database Transaksi
public class Trans {
    private String name;
    private String quantity;
    private String from;
    private String to;

    public Trans (){

    }

    //Konstraktor dari class Trans

    public Trans(String name, String quantity, String from, String to) {
        this.name = name;
        this.quantity = quantity;
        this.from = from;
        this.to = to;
    }

    //Getter dari Konstraktor Trans (name, quantity, from, to)
    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}

