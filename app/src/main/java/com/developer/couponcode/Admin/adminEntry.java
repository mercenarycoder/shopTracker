package com.developer.couponcode.Admin;

public class adminEntry {
    String name,phone,refferal,price,decription,rate;

    public adminEntry() {
    }

    public adminEntry(String name, String phone, String refferal, String price, String decription,String rate) {
        this.name = name;
        this.phone = phone;
        this.refferal = refferal;
        this.price = price;
        this.decription = decription;
        this.rate=rate;
    }

    public String getRate() {
        return rate;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getRefferal() {
        return refferal;
    }

    public String getPrice() {
        return price;
    }

    public String getDecription() {
        return decription;
    }
}
