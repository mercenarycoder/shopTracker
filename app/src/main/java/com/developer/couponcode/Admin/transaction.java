package com.developer.couponcode.Admin;

public class transaction {
    String phn,point;

    public transaction(String phn, String point) {
        this.phn = phn;
        this.point = point;
    }

    public String getPhn() {
        return phn;
    }

    public String getPoint() {
        return point;
    }
}
