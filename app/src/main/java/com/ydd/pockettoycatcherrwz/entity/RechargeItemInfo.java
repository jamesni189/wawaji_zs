package com.ydd.pockettoycatcherrwz.entity;

import java.io.Serializable;

/**
 * 充值对话款列表页item
 */
public class RechargeItemInfo implements Serializable {
    /**
     * 描述（多送了500）
     */
    public String desc;
    /**
     * 游戏币 number
     */
    public int money;
    /**
     * 人民币 numbe
     */
    public String price;

    /**
     * id
     */
    public int id;

    /**
     * 标牌
     */
    public String mark;

    @Override
    public String toString() {
        return "RechargeItemInfo{" +
                "desc='" + desc + '\'' +
                ", money='" + money + '\'' +
                ", price='" + price + '\'' +
                ", id='" + id + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }
}
