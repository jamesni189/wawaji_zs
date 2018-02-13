package com.ydd.pockettoycatcherrwz.entity;

import java.io.Serializable;

/**
 * Created by jia on 17/11/9.
 */

public class RoomLogInfo implements Serializable{

  /*  public List<LiveRecord> data;*/

    private String img;

    private String name;


    private String createTime;

    private String url;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
