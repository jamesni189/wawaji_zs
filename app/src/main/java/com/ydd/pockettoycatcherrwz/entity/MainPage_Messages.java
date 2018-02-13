package com.ydd.pockettoycatcherrwz.entity;

import java.io.Serializable;

/**
 * Created by C-yl on 2017/12/4.
 */

public class MainPage_Messages implements Serializable{
    public   int id;
    public String createTime;
    public String updateTime;
    public String title;
    public String message;

    @Override
    public String toString() {
        return "MainPage_Messages{" +
                "id=" + id +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
