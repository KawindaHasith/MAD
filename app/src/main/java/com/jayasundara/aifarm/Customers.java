package com.jayasundara.aifarm;

public class Customers {
    private String customer;
    private  String address;
    private  String contactNo;
    private String email;

    public Customers(String customer, String address, String contactNo, String email) {
        this.customer = customer;
        this.address = address;
        this.contactNo = contactNo;
        this.email = email;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
