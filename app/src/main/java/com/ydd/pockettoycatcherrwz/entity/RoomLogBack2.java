package com.ydd.pockettoycatcherrwz.entity;

import java.util.List;

/**
 * Created by jia on 17/11/9.
 */

public class RoomLogBack2 {

    private List<RoomLogInfo> data;
    private int errCode;
    private String errMsg;

    public List<RoomLogInfo> getData() {
        return data;
    }

    public void setData(List<RoomLogInfo> data) {
        this.data = data;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }


    //  {"errCode":0,"errMsg":"OK","data":[{"name":"可爱兔","img":"https://yingdd.oss-cn-hangzhou.aliyuncs.com/5118124efb97baad87c6d68e67113fdc.png","url":"https://yingdd.oss-cn-hangzhou.aliyuncs.com/1afca5878c7d4c67cf258fe2a3625ab3.mp4","createTime":"2018-02-07 23:31:36"},{"name":"可爱兔","img":"https://yingdd.oss-cn-hangzhou.aliyuncs.com/5118124efb97baad87c6d68e67113fdc.png","url":"https://yingdd.oss-cn-hangzhou.aliyuncs.com/d18f85d26d0f3364d048db9226a0afeb.mp4","createTime":"2018-02-07 20:14:23"}]}

}
