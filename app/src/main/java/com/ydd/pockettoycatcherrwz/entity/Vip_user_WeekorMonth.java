package com.ydd.pockettoycatcherrwz.entity;

import java.io.Serializable;

/**
 * Created by C-yl on 2017/12/15.
 */

public class Vip_user_WeekorMonth implements Serializable {
    /**
     * 周卡会员
     */
     public Vip_Week week;
    /**
     * 月卡会员
     */
     public Vip_Month mouth;

    @Override
    public String toString() {
        return "Vip_user_WeekorMonth{" +
                "week=" + week +
                ", mouth=" + mouth +
                '}';
    }
}
