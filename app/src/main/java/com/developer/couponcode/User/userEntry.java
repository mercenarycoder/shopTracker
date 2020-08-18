package com.developer.couponcode.User;

public class userEntry {
    String name,phone,refferal,price,decription,rate;

    public userEntry() {
    }

    public userEntry(String name, String phone, String refferal, String price, String decription,String rate) {
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
