package com.developer.couponcode.Admin;

public class Users {
    String number,points,minus;

    public Users(String number, String points,String minus) {
        this.number = number;
        this.points = points;
        this.minus=minus;
    }

    public String getMinus() {
        return minus;
    }

    public String getNumber() {
        return number;
    }

    public String getPoints() {
        return points;
    }
}
