package com.jayasundara.aifarm;

public class Products {
    private String product;
    private  String type;
    private  String fixrate;
    private String note;

    public Products(String product, String type, String fixrate, String note) {
        this.product = product;
        this.type = type;
        this.fixrate = fixrate;
        this.note = note;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFixrate() {
        return fixrate;
    }

    public void setFixrate(String fixrate) {
        this.fixrate = fixrate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
