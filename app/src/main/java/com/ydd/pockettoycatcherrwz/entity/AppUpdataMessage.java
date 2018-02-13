package com.ydd.pockettoycatcherrwz.entity;

import java.io.Serializable;

/**
 * Created by C-yl on 2017/12/21.
 */

public class AppUpdataMessage implements Serializable{
    public String mesage;
    public int android_update;
    public String android_url;

    @Override
    public String toString() {
        return "AppUpdataMessage{" +
                "mesage='" + mesage + '\'' +
                ", android_update=" + android_update +
                ", android_url='" + android_url + '\'' +
                '}';
    }
}
