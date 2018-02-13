package com.ydd.pockettoycatcherrwz.entity;

import java.io.Serializable;

/**
 * Created by C-yl on 2017/11/24.
 */

public class ViewPagerIndicateMessage implements Serializable {


    /**
     * 轮播条id
     */
    private int id;

    /**
     * 轮播条名字
     */
    private String name;

    @Override
    public String toString() {
        return "ViewPagerIndicateMessage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
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
}
