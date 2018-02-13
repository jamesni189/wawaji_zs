package com.ydd.pockettoycatcherrwz.entity;

import java.io.Serializable;

/**
 * Created by C-yl on 2017/12/15.
 */

public class Vip_Month  implements Serializable {
    /**
     * 每日领取
     */
    public int dayMoney;
    /**
     * 总领取
     */
    public int totalMoney;
    /**
     * 领取记录
     */
    public int receiveStatus;
    /**
     * 到期时间
     */
    public String erpireDate;
    /**
     * 领取记录
     */
    public String receiveRecord;

    /*
    * 碎片总数
    * */
    public int fragmentCounts;
}
