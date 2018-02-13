package com.ydd.pockettoycatcherrwz.entity;

import java.io.Serializable;

/**
 * Created by C-yl on 2017/12/19.
 */

public class WeekOrMouthBean implements Serializable {

    public String title;
    public float price;
    public int money;
    public String desc;
    public String detail;
    public String mark;

    @Override
    public String toString() {
        return "WeekOrMouthBean{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", money=" + money +
                ", desc='" + desc + '\'' +
                ", detail='" + detail + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }
}
