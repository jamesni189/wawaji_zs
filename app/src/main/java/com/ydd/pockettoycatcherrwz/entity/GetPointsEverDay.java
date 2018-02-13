package com.ydd.pockettoycatcherrwz.entity;

/**
 * Created by C-yl on 2017/12/14.
 */

public class GetPointsEverDay {
    public String title;
    public String desc;
    public String type;
    public String reward;
    public  int completeStatus;

    @Override
    public String toString() {
        return "GetPointsEverDay{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", type='" + type + '\'' +
                ", reward='" + reward + '\'' +
                ", completeStatus=" + completeStatus +
                '}';
    }
}
