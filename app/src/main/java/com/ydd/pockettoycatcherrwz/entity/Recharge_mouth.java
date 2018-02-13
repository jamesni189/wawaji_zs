package com.ydd.pockettoycatcherrwz.entity;

import java.io.Serializable;

/**
 * Created by C-yl on 2017/12/15.
 */

public class Recharge_mouth implements Serializable {
    public String price;
    public int money;
    public String desc;
    public String mark;
    public String detail;
   public String title;

    @Override
    public String toString() {
        return "Recharge_mouth{" +
                "price='" + price + '\'' +
                ", money=" + money +
                ", desc='" + desc + '\'' +
                ", mark='" + mark + '\'' +
                ", detail='" + detail + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
