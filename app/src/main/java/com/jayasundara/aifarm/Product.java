package com.jayasundara.aifarm;

public class Product {

    private int id;
    private  String name;
    private  String qty;
    private String total;

    public Product(int id, String name, String qty, String total) {
        this.id = id;
        this.name = name;
        this.qty = qty;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
