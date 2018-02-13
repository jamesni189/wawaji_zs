package com.ydd.pockettoycatcherrwz.entity;

import java.io.Serializable;

/**
 * Created by C-yl on 2017/12/16.
 */

public class RechargeGridBean implements Serializable{
    public String price;
    public int money;
    public String desc;
    public int sort;
    public int id;
    public String mark;
    public String kinds;
    public String detail;
    public String title;

    public RechargeGridBean(String price, int money, String desc, int sort, int id, String mark, String kinds, String detail, String title) {
        this.price = price;
        this.money = money;
        this.desc = desc;
        this.sort = sort;
        this.id = id;
        this.mark = mark;
        this.kinds = kinds;
        this.detail = detail;
        this.title = title;
    }

    @Override
    public String toString() {
        return "RechargeGridBean{" +
                "price='" + price + '\'' +
                ", money=" + money +
                ", desc='" + desc + '\'' +
                ", sort=" + sort +
                ", id=" + id +
                ", mark='" + mark + '\'' +
                ", kinds='" + kinds + '\'' +
                ", detail='" + detail + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
