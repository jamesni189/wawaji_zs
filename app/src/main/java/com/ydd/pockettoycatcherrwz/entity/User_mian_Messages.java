package com.ydd.pockettoycatcherrwz.entity;

/**
 * Created by C-yl on 2017/12/15.
 */

public class User_mian_Messages  {
    /**
     * 会员过期时间
     */
    public String erpireDate;
    /**
     * 头像
     */
    public String avatar;
    /**
     * 是否是会员
     */
    public int hasMember;
    /**
     * 抓取记录
     */
    public int grabCounts;
    /**
     * 娃娃数量
     */
    public int dollCounts;
    /**
     * 昵称
     */
    public String nickname;

    public int fragmentCounts;

    public int points;


    @Override
    public String toString() {
        return "User_mian_Messages{" +
                "erpireDate='" + erpireDate + '\'' +
                ", avatar='" + avatar + '\'' +
                ", hasMember=" + hasMember +
                ", grabCounts=" + grabCounts +
                ", dollCounts=" + dollCounts +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
