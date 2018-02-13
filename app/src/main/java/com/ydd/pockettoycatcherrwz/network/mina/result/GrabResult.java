package com.ydd.pockettoycatcherrwz.network.mina.result;

/**
 * 抓取结果
 */
public class GrabResult {
    public String cmd;
    /**
     * 机器编号
     */
    public String vmc_no;
    /**
     * 1抓取成功，0抓取失败，3未收到消息
     * // 1, //抓取结果  1：抓中  0：未抓中
     */
    public int value;
    /*
    * // 5: 五福碎片  4：未抓中也没有奖励  3：未抓中但是获得碎片   2：未抓中获得积分  1：未抓中但是获得钻石
    * */
    public int prizeType;
    /*
    *  // 未抓中时，获得的钻石 或者积分  或者碎片的数量
    * */
    public int num;

    /*
    *  // 每局结束后的保护时间
    * */
    public int protection_seconds;

    public int member_count;

    public String nickname;

    public String img; // 碎片的图片链接

    public String name; // 获得的商品名称


}
